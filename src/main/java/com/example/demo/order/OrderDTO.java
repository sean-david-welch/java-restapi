package com.example.demo.order;

import java.time.LocalDateTime;

import com.example.demo.order.Order.Status;

public record OrderDTO(
        String id,
        LocalDateTime orderDate,
        String customerId,
        Status status) {
    public String getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Status getStatus() {
        return status;
    }
}
