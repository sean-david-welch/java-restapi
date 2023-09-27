package com.example.demo.authentication;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.role.RoleRepository;
import com.example.demo.user.UserRepository;

import com.example.demo.role.Role;
import com.example.demo.user.User;

import jakarta.transaction.Transactional;
// import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    // private final ObjectMapper objectMapper;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
            // ObjectMapper objectMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        // this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Username already taken");
        }

        Role userRole = roleRepository.findByAuthority("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));

        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setAuthorities(Set.of(userRole));

        return userRepository.save(newUser);
    }

}
