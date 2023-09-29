package com.example.demo.orderdetails;

import org.springframework.stereotype.Service;

import com.example.demo.data.OrderDetailsDTO;
import com.example.demo.order.Order;
import com.example.demo.order.OrderRepository;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;

import jakarta.transaction.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderDetailsService(OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository,
            OrderRepository orderRepository) {

        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;

    }

    public List<OrderDetailsDTO> GetOrderDetails() {
        List<OrderDetails> orderDetails = orderDetailsRepository.findAll();

        return orderDetails.stream()
                .map(OrderDetailsDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<OrderDetailsDTO> GetOrderDetailById(String orderDetailId) {
        Optional<OrderDetails> orderDetail = orderDetailsRepository.findOrderDetailsById(orderDetailId);

        return orderDetail.map(OrderDetailsDTO::new);
    }

    @Transactional
    public OrderDetailsDTO CreateOrderDetails(OrderDetailsDTO orderDetailsDTO) {
        Optional<OrderDetails> orderDetailsOptional = orderDetailsRepository
                .findOrderDetailsById(orderDetailsDTO.getId());
        Optional<Order> orderOptional = orderRepository.findById(orderDetailsDTO.getOrderId());
        Optional<Product> productOptional = productRepository.findById(orderDetailsDTO.getProductId());

        if (orderDetailsOptional.isPresent() || !orderOptional.isPresent() || !productOptional.isPresent()) {
            throw new IllegalStateException("OrderDetails or Order or Product not found");
        }

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(UUID.randomUUID().toString());
        orderDetails.setOrder(orderOptional.get());
        orderDetails.setProduct(productOptional.get());
        orderDetails.setQuantity(orderDetailsDTO.getQuantity());
        orderDetails.setUnitPrice(orderDetailsDTO.getUnitPrice());

        OrderDetails savedOrderDetails = orderDetailsRepository.save(orderDetails);

        return new OrderDetailsDTO(savedOrderDetails);
    }

    @Transactional
    public OrderDetailsDTO UpdateOrderDetails(String id, OrderDetailsDTO orderDetailsDTO) {
        Optional<OrderDetails> orderDetailsOptional = orderDetailsRepository.findOrderDetailsById(id);
        Optional<Order> orderOptional = orderRepository.findById(orderDetailsDTO.getOrderId());
        Optional<Product> productOptional = productRepository.findById(orderDetailsDTO.getProductId());

        if (!orderDetailsOptional.isPresent() || !orderOptional.isPresent() || !productOptional.isPresent()) {
            throw new IllegalStateException("OrderDetails or Order or Product not found");
        }

        OrderDetails existingOrderDetails = orderDetailsOptional.get();
        existingOrderDetails.setOrder(orderOptional.get());
        existingOrderDetails.setProduct(productOptional.get());
        existingOrderDetails.setQuantity(orderDetailsDTO.getQuantity());
        existingOrderDetails.setUnitPrice(orderDetailsDTO.getUnitPrice());

        OrderDetails updatedOrderDetails = orderDetailsRepository.save(existingOrderDetails);

        return new OrderDetailsDTO(updatedOrderDetails);
    }

    public void RemoveOrderDetails(String id) {
        boolean exists = orderDetailsRepository.existsById(id);

        if (!exists) {
            throw new IllegalStateException("Product does not exists" + id);
        }

        orderDetailsRepository.deleteById(id);
    }
}
