package com.example.demo.authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.LoginRequestDTO;
import com.example.demo.data.LoginResponseDTO;
import com.example.demo.data.UserRequestDTO;
import com.example.demo.data.UserResponseDTO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserResponseDTO RegisterUser(@Valid @RequestBody UserRequestDTO UserRequestDTO) {
        return authenticationService.registerUser(UserRequestDTO.getUsername(), UserRequestDTO.getEmail(),
                UserRequestDTO.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO Login(@RequestBody LoginRequestDTO body, HttpServletResponse response) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword(), response);
    }

    @PostMapping("/logout")
    public void Logout(HttpServletResponse response) {
        authenticationService.logout(response);
    }

    @PutMapping("/update-user/{userId}")
    public UserResponseDTO updateUser(@PathVariable String userId, @Valid @RequestBody UserRequestDTO UserRequestDTO) {
        return authenticationService.updateUser(userId, UserRequestDTO.getUsername(), UserRequestDTO.getEmail(),
                UserRequestDTO.getPassword());
    }

    @PutMapping("/update-user-roles/{userId}")
    public UserResponseDTO updateUserRoles(@PathVariable String userId, @RequestBody Set<String> newRoles) {
        return authenticationService.updateUserRoles(userId, newRoles);
    }

}
