package com.example.demo.order;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.OrderDTO;

@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> GetOrders() {
        return orderService.GetOrders();
    }

    @GetMapping(path = "{orderId}")
    public Optional<OrderDTO> FetchOrderById(@PathVariable("orderId") String orderId) {
        return orderService.GetOrderDetail(orderId);
    }

    @PostMapping
    public OrderDTO PostOrder(@RequestBody OrderDTO order) {
        return orderService.CreateOrder(order);
    }

    @PutMapping(path = "{orderId}")
    public OrderDTO PutOrder(@PathVariable("orderId") String orderId, @RequestBody OrderDTO order) {
        return orderService.UpdateOrder(order, orderId);
    }

    @DeleteMapping(path = "{orderId}")
    public ResponseEntity<String> DeleteOrder(@PathVariable("orderId") String orderId) {
        orderService.RemoveOrder(orderId);

        return ResponseEntity.ok("Order with Id" + orderId + " has been deleted.");
    }
}
