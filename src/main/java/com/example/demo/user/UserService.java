package com.example.demo.user;

import com.example.demo.role.RoleRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ObjectMapper objectMapper) {

        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
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
