package com.example.demo.data;

import com.example.demo.orderdetails.OrderDetails;

public class OrderDetailsDTO {
    private String id;
    private String productId;
    private String productName;
    private String orderId;
    private String orderStatus;
    private Integer quantity;
    private Float unitPrice;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(OrderDetails orderDetails) {
        this.id = orderDetails.getId();
        this.productId = (orderDetails.getProduct() != null) ? orderDetails.getProduct().getId() : null;
        this.productName = (orderDetails.getProduct() != null) ? orderDetails.getProduct().getName() : null;
        this.orderId = (orderDetails.getOrder() != null) ? orderDetails.getOrder().getId() : null;
        this.orderStatus = (orderDetails.getOrder() != null) ? orderDetails.getOrder().getStatus().name() : null;
        this.quantity = orderDetails.getQuantity();
        this.unitPrice = orderDetails.getUnitPrice();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }
}
