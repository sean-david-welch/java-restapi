package com.example.demo.order;

// import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

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

    public void CreateOrder(Order order) {
        Optional<Order> orderOptional = orderRepository.findOrderById(order.getId());
        Optional<Customer> customerOptional = customerRepository.findById(order.getCustomer().getId());

        if (orderOptional.isPresent() || !customerOptional.isPresent()) {
            throw new IllegalStateException("This order already exists or the customer does not exist");
        }

        order.setId(UUID.randomUUID().toString());

        orderRepository.save(order);
    }

}
