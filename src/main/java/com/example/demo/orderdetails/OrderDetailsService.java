package com.example.demo.orderdetails;

import org.springframework.stereotype.Service;

// import com.example.demo.order.OrderService;
import com.example.demo.order.Order;
import com.example.demo.order.OrderRepository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;

    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository, OrderRepository orderRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderDetails> GetOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public Optional<OrderDetails> GetOrderDetailById(String orderDetailId) {
        return orderDetailsRepository.findOrderDetailsById(orderDetailId);
    }

    public void CreateOrderDetails(OrderDetails orderDetails) {
        Optional<OrderDetails> orderDetailsOptional = orderDetailsRepository.findOrderDetailsById(orderDetails.getId());
        Optional<Order> orderOptional = orderRepository.findById(orderDetails.getOrder_id().getId());

        if (orderDetailsOptional.isPresent() || !orderOptional.isPresent()) {
            throw new IllegalStateException("The order detail already exists or the order itself is non existant");
        }

        orderDetails.setId(UUID.randomUUID().toString());

        orderDetailsRepository.save(orderDetails);
    }
}
