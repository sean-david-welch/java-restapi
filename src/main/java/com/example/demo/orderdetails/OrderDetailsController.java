package com.example.demo.orderdetails;

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
@RequestMapping(path = "/api/orders-details")
public class OrderDetailsController {
    private final OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping
    public List<OrderDetails> FetchOrderDetails() {
        return orderDetailsService.GetOrderDetails();
    }

    @GetMapping(path = "/{orderDetailId}")
    public Optional<OrderDetails> FetchOrderDetailsById(@PathVariable("orderDetailId") String orderDetailId) {
        return orderDetailsService.GetOrderDetailById(orderDetailId);
    }

    @PostMapping
    public void PostOrderDetails(@RequestBody OrderDetails orderDetails) {
        orderDetailsService.CreateOrderDetails(orderDetails);
    }

    @PutMapping(path = "{orderDetailId}")
    public void PutOrderDetails(@PathVariable("orderDetailId") String orderDetailsId,
            @RequestBody OrderDetails orderDetails) {
        orderDetailsService.UpdateOrderDetails(orderDetails, orderDetailsId);
    }

    @DeleteMapping(path = "{orderDetailId}")
    public void DeleteOrderDetails(@PathVariable("orderDetailId") String orderDetailsId) {
        orderDetailsService.RemoveOrderDetails(orderDetailsId);
    }
}
