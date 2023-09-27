package com.example.demo.authentication;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.LoginResponseDTO;
import com.example.demo.data.LoginRequestDTO;
import com.example.demo.data.UserDTO;
import com.example.demo.user.User;

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
    public User RegisterUser(@Valid @RequestBody UserDTO userDTO) {
        return authenticationService.registerUser(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginRequestDTO body, HttpServletResponse response) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword(), response);

    }

    @PutMapping("/update-user/{userId}")
    public User updateUser(@PathVariable String userId, @Valid @RequestBody UserDTO userDTO) {
        return authenticationService.updateUser(userId, userDTO.getUsername(), userDTO.getEmail(),
                userDTO.getPassword());
    }

    @PutMapping("/update-user-roles/{userId}")
    public User updateUserRoles(@PathVariable String userId, @RequestBody Set<String> newRoles) {
        return authenticationService.updateUserRoles(userId, newRoles);
    }

}
