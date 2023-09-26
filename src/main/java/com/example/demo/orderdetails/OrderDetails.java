package com.example.demo.orderdetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.example.demo.order.Order;
import com.example.demo.product.Product;

import jakarta.persistence.Column;

@Entity
@Table(name = "orderdetail")
public class OrderDetails {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Float unitPrice;

    public OrderDetails() {
    }

    public OrderDetails(String id, Product product, Order order, Integer quantity, Float unitPrice) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductID() {
        return this.product != null ? this.product.getId() : null;
    }

    public void setProductID(Product product) {
        this.product = product;
    }

    public String getProduct_name() {
        return this.product != null ? this.product.getName() : null;
    }

    public Order getOrderID() {
        return order;
    }

    public void setOrderID(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }
}
