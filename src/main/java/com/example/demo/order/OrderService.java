package com.example.demo.order;

import org.springframework.stereotype.Service;

import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerRepository;
import com.example.demo.data.OrderDTO;

import jakarta.transaction.Transactional;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public List<OrderDTO> GetOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<OrderDTO> GetOrderDetail(String orderId) {
        Optional<Order> order = orderRepository.findOrderById(orderId);

        return order.map(OrderDTO::new);
    }

    @Transactional
    public OrderDTO CreateOrder(OrderDTO orderDTO) {
        Optional<Order> orderOptional = orderRepository.findOrderById(orderDTO.getId());
        Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());

        if (orderOptional.isPresent() || !customerOptional.isPresent()) {
            throw new IllegalStateException("The customer does not exist or this order already exists");
        }

        Order order = new Order();

        order.setId(UUID.randomUUID().toString());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(Order.Status.valueOf(orderDTO.getStatus()));
        order.setCustomer(customerOptional.get());

        Order savedOrder = orderRepository.save(order);

        return new OrderDTO(savedOrder);
    }

    @Transactional
    public OrderDTO UpdateOrder(OrderDTO orderDTO, String orderId) {
        Optional<Order> orderOptional = orderRepository.findOrderById(orderId);
        Optional<Customer> customerOptional = customerRepository.findById(orderDTO.getCustomerId());

        if (!orderOptional.isPresent() || !customerOptional.isPresent()) {
            throw new IllegalStateException("This order doesn't exist or the customer doesn't exist");
        }

        Order existingOrder = orderOptional.get();

        existingOrder.setOrderDate(orderDTO.getOrderDate());
        existingOrder.setCustomer(customerOptional.get());
        existingOrder.setStatus(Order.Status.valueOf(orderDTO.getStatus()));

        Order updatedOrder = orderRepository.save(existingOrder);

        return new OrderDTO(updatedOrder);
    }

    public void RemoveOrder(String orderId) {
        boolean exists = orderRepository.existsById(orderId);

        if (!exists) {
            throw new IllegalStateException("Order does not exist" + orderId);
        }

        orderRepository.deleteById(orderId);
    }

}
