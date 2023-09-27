package com.example.demo.authentication;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.role.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.utils.TokenService;
import com.example.demo.data.LoginResponseDTO;
import com.example.demo.role.Role;
import com.example.demo.user.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(
            UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
            TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO loginUser(String username, String password) {

        try {
            logger.info("Attempting to authenticate user: {}", username);

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenService.generateJwt(auth);

            logger.info("User authenticated successfully. Generated token.");

            return new LoginResponseDTO(userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")), token);
        } catch (AuthenticationException error) {
            logger.error("Authentication failed for user: {}", username);

            throw new BadCredentialsException("Invalid username/password supplied");

        }
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

    public User updateUser(String userId, String newUsername, String newEmail, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalStateException("User not found");
        }

        User existingUser = userOptional.get();

        if (newUsername != null) {
            Optional<User> byUsername = userRepository.findByUsername(newUsername);
            if (byUsername.isPresent() && !byUsername.get().getId().equals(userId)) {
                throw new IllegalStateException("Username already taken");
            }
            existingUser.setUsername(newUsername);
        }

        if (newEmail != null) {
            existingUser.setEmail(newEmail);
        }

        if (newPassword != null) {
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }

        return userRepository.save(existingUser);
    }

    public User updateUserRoles(String userId, Set<String> newRoles) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalStateException("User not found");
        }

        User existingUser = userOptional.get();
        Set<Role> roles = newRoles.stream()
                .map(roleName -> roleRepository.findByAuthority(roleName)
                        .orElseThrow(() -> new IllegalStateException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        existingUser.setAuthorities(roles);

        return userRepository.save(existingUser);
    }

}
