package com.example.demo.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(path = "/api/users")
public class UserContoller {

    private final UserService userService;

    public UserContoller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<ObjectNode> GetAllUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{userId}")
    public ObjectNode GetUserById(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public void PostUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping(path = "{userId}")
    public void PutUser(@RequestBody User user, @PathVariable String userId) {
        userService.updateUser(userId, user);
    }

    @DeleteMapping(path = "{userId}")
    public void DeleteUser(@PathVariable String userId) {
        userService.removeUser(userId);
    }
}
