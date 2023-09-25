package com.example.demo.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "name")
    private String name;

    public Customer() {
    }

    public Customer(String id, User user, String name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }

    @JsonGetter("userId")
    public String getUserId() {
        return this.user != null ? this.user.getId() : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
