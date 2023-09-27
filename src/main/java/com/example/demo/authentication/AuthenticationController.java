package com.example.demo.authentication;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.LoginResponseDTO;
import com.example.demo.data.LoginRequestDTO;
import com.example.demo.data.UserDTO;
import com.example.demo.user.User;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public User RegisterUser(@Valid @RequestBody UserDTO userDTO) {
        return authenticationService.registerUser(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginRequestDTO body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }

}
