package com.example.demo.data;

import com.example.demo.user.User;
import com.example.demo.role.Role;
import java.util.Set;
import java.util.stream.Collectors;

public class UserResponseDTO {
    private String id;
    private String username;
    private String email;
    private Set<Role> roles;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getAuthorities().stream()
                .filter(Role.class::isInstance)
                .map(Role.class::cast)
                .collect(Collectors.toSet());
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
