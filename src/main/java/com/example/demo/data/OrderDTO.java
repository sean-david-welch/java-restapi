package com.example.demo.data;

import java.time.LocalDateTime;

import com.example.demo.order.Order;

public class OrderDTO {

    private String id;
    private LocalDateTime orderDate;
    private String customerId;
    private String status;

    public OrderDTO() {

    }

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.customerId = (order.getCustomer() != null) ? order.getCustomer().getId() : null;
        this.status = (order.getStatus() != null) ? order.getStatus().name() : null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
