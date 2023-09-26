package com.example.demo.order;

// import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;

import jakarta.transaction.Transactional;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> GetOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> GetOrderDetail(String orderId) {
        return orderRepository.findOrderById(orderId);
    }

    @Transactional
    public void CreateOrder(OrderDTO orderDTO) {
        // Check if customer exists
        Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());
        if (!customerOptional.isPresent()) {
            throw new IllegalStateException("The customer does not exist");
        }
        Customer customer = customerOptional.get();

        // Create new Order
        Order order = new Order(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                customer,
                orderDTO.getStatus());

        // Save the new Order
        orderRepository.save(order);
    }

    @Transactional
    public void UpdateOrder(Order order, String orderId) {
        Optional<Order> orderOptional = orderRepository.findOrderById(order.getId());
        Optional<Customer> customerOptional = customerRepository.findById(order.getCustomer().getId());

        if (orderOptional.isPresent() || !customerOptional.isPresent()) {
            throw new IllegalStateException("This order already exists or the customer does not exist");
        }

        Order existingOrder = orderOptional.get();

        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setCustomer(order.getCustomer());
        existingOrder.setStatus(order.getStatus());

        orderRepository.save(existingOrder);
    }

    public void RemoveOrder(String orderId) {
        boolean exists = orderRepository.existsById(orderId);

        if (!exists) {
            throw new IllegalStateException("Order does not exist" + orderId);
        }

        orderRepository.deleteById(orderId);
    }

}
