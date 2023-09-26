package com.example.demo.order;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> GetOrders() {
        return orderService.GetOrders();
    }

    @GetMapping(path = "{orderId}")
    public Optional<Order> FetchOrderById(@PathVariable("orderId") String orderId) {
        return orderService.GetOrderDetail(orderId);
    }

    @PostMapping
    public void PostOrder(@RequestBody OrderDTO order) {
        orderService.CreateOrder(order);
    }

    @PutMapping(path = "{orderId}")
    public void PutOrder(@PathVariable("orderId") String orderId, @RequestBody Order order) {
        orderService.UpdateOrder(order, orderId);
    }

    @DeleteMapping(path = "{orderId}")
    public void DeleteOrder(@PathVariable("orderId") String orderId) {
        orderService.RemoveOrder(orderId);
    }
}
