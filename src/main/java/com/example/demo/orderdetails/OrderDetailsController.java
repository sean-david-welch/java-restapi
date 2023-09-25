package com.example.demo.orderdetails;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/orders-details")
public class OrderDetailsController {
    private final OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping
    public List<OrderDetails> GetOrderDetails() {
        return orderDetailsService.GetOrderDetails();
    }

    @GetMapping(path = "/{orderDetailId}")
    public Optional<OrderDetails> getOrderDetailsById(@PathVariable("orderDetailId") String orderDetailId) {
        return orderDetailsService.GetOrderDetailById(orderDetailId);
    }

    @GetMapping(path = "/{orderId}")
    public List<OrderDetails> getOrderDetailsByOrderId(@PathVariable("orderId") String orderId) {
        return orderDetailsService.GetOrderDetailsByOrderId(orderId);
    }

}
