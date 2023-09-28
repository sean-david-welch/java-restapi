package com.example.demo.data;

import java.util.Set;

import com.example.demo.role.Role;
import com.example.demo.user.User;

public class UserResponseDTO {
    private String id;
    private String username;
    private String email;

    public UserResponseDTO() {
    }

    public UserResponseDTO(String id, String username, String email, Set<Role> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserResponseDTO mapToDTO(User user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

}
