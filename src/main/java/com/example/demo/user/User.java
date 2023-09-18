package com.example.demo.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import jakarta.persistence.Column;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "is_superuser")
    private boolean is_superuser;

    public User() {}

    public User(String id, String name, String email, String password, boolean is_active, boolean is_superuser) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.is_active = is_active;
        this.is_superuser = is_superuser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean getIs_superuser() {
        return is_superuser;
    }

    public void setIs_superuser(boolean is_superuser) {
        this.is_superuser = is_superuser;
    }
}