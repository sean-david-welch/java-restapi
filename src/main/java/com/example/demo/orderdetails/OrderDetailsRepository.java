package com.example.demo.orderdetails;

import java.util.Optional;
import java.util.List;

import com.example.demo.order.Order;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
    Optional<OrderDetails> findOrderDetailsById(String id);

    List<OrderDetails> findByOrderId(Order order);
}
