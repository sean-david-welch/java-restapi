package com.example.demo.authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.LoginResponseDTO;
import com.example.demo.data.RegistrationDTO;
import com.example.demo.data.UserDTO;
import com.example.demo.user.User;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin("http://localhost:8080/")
@RequestMapping(path = "/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public User RegisterUser(@RequestBody UserDTO userDTO) {
        return authenticationService.registerUser(userDTO.username(), userDTO.email(), userDTO.password());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return authenticationService.loginUser(body.username(), body.password());
    }

}
