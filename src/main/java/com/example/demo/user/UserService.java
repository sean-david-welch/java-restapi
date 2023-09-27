package com.example.demo.user;

import com.example.demo.role.Role;
import com.example.demo.role.RoleRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;
import java.util.Optional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;

    }

    public List<ObjectNode> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            ObjectNode node = objectMapper.convertValue(user, ObjectNode.class);
            node.remove("password");
            node.remove("credentialsNonExpired");
            node.remove("accountNonExpired");
            node.remove("accountNonLocked");
            return node;
        }).collect(Collectors.toList());
    }

    public ObjectNode getUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User not found");
        }

        User user = optionalUser.get();
        ObjectNode node = objectMapper.convertValue(user, ObjectNode.class);
        node.remove("password");
        node.remove("credentialsNonExpired");
        node.remove("accountNonExpired");
        node.remove("accountNonLocked");

        return node;
    }

    @Transactional
    public void createUser(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Username already taken");
        }

        Role userRole = roleRepository.findByAuthority("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));

        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Set.of(userRole));

        userRepository.save(user);
    }

    @Transactional
    public void updateUser(String userId, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalStateException("User not found");
        }

        User existingUser = userOptional.get();

        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
            Optional<User> byUsername = userRepository.findByUsername(updatedUser.getUsername());
            if (byUsername.isPresent() && !byUsername.get().getId().equals(userId)) {
                throw new IllegalStateException("Username already taken");
            }
            existingUser.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRepository.save(existingUser);
    }

    public void removeUser(String userId) {
        boolean exists = userRepository.existsById(userId);

        if (!exists) {
            throw new IllegalStateException("User does not exist" + userId);
        }

        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

    }
}
