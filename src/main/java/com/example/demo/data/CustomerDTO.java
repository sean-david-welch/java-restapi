package com.example.demo.data;

import com.example.demo.customer.Customer;

public class CustomerDTO {

    private String id;
    private String userId;
    private String name;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.userId = (customer.getUser() != null) ? customer.getUser().getId() : null;
        this.name = customer.getName();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
