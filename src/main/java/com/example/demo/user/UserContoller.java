package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.UserResponseDTO;

@RestController
@RequestMapping(path = "/api/users")
public class UserContoller {

    private final UserService userService;

    public UserContoller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDTO> GetAllUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{userId}")
    public Optional<UserResponseDTO> GetUserById(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<String> DeleteUser(@PathVariable String userId) {
        userService.removeUser(userId);

        return ResponseEntity.ok("User with ID " + userId + " has been deleted.");

    }
}
