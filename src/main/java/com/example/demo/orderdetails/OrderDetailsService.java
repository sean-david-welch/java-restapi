package com.example.demo.orderdetails;

import org.springframework.stereotype.Service;

// import com.example.demo.order.OrderService;
import com.example.demo.order.Order;
import com.example.demo.order.OrderRepository;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;

import jakarta.transaction.Transactional;

import java.util.UUID;
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

    public List<OrderDetails> GetOrderDetails() {
        return orderDetailsRepository.findAll();
    }

    public Optional<OrderDetails> GetOrderDetailById(String orderDetailId) {
        return orderDetailsRepository.findOrderDetailsById(orderDetailId);
    }

    @Transactional
    public void CreateOrderDetails(OrderDetails orderDetails) {
        Optional<OrderDetails> orderDetailsOptional = orderDetailsRepository.findOrderDetailsById(orderDetails.getId());
        Optional<Order> orderOptional = orderRepository.findById(orderDetails.getOrderID().getId());

        if (orderDetailsOptional.isPresent() || !orderOptional.isPresent()) {
            throw new IllegalStateException("The order detail already exists or the order itself is non existant");
        }

        orderDetails.setId(UUID.randomUUID().toString());

        orderDetailsRepository.save(orderDetails);
    }

    @Transactional
    public void UpdateOrderDetails(OrderDetails orderDetails, String Id) {
        Optional<OrderDetails> orderDetailsOptional = orderDetailsRepository.findOrderDetailsById(orderDetails.getId());
        Optional<Order> orderOptional = orderRepository.findOrderById(orderDetails.getOrderID().getId());
        Optional<Product> producOptional = productRepository.findProductById(orderDetails.getProductID());

        if (orderDetailsOptional.isPresent() || !orderOptional.isPresent()) {
            throw new IllegalStateException("The order detail already exists or the order itself is non existant");
        }

        OrderDetails existingDetails = orderDetailsOptional.get();
        Product product = producOptional.get();

        existingDetails.setOrderID(orderDetails.getOrderID());
        existingDetails.setProductID(product);
        existingDetails.setOrderID(orderDetails.getOrderID());
        existingDetails.setQuantity(orderDetails.getQuantity());
        existingDetails.setUnitPrice(orderDetails.getUnitPrice());

        orderDetailsRepository.save(existingDetails);
    }

    public void RemoveOrderDetails(String id) {
        boolean exists = orderDetailsRepository.existsById(id);

        if (!exists) {
            throw new IllegalStateException("Product does not exists" + id);
        }

        orderDetailsRepository.deleteById(id);
    }
}
