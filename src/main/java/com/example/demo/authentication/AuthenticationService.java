package com.example.demo.authentication;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.example.demo.data.UserResponseDTO;
import com.example.demo.role.Role;
import com.example.demo.user.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

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

    public LoginResponseDTO loginUser(String username, String password, HttpServletResponse response) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenService.generateJwt(auth);

            Cookie jwtCookie = new Cookie("access_token", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(false);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            User user = userRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("User not found"));

            UserResponseDTO userResponseDTO = new UserResponseDTO(user);
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(userResponseDTO, token);

            return loginResponseDTO;

        } catch (AuthenticationException error) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    public void logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("access_token", null);

        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        response.addCookie(jwtCookie);
    }

    public UserResponseDTO registerUser(String username, String email, String password) {
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

        userRepository.save(newUser);

        return new UserResponseDTO(newUser);
    }

    public UserResponseDTO updateUser(String userId, String newUsername, String newEmail, String newPassword) {
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

        userRepository.save(existingUser);

        return new UserResponseDTO(existingUser);
    }

    public UserResponseDTO updateUserRoles(String userId, Set<String> newRoles) {
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

        userRepository.save(existingUser);

        return new UserResponseDTO(existingUser);
    }

}
