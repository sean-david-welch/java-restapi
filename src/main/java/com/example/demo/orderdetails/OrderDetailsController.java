package com.example.demo.orderdetails;

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

import com.example.demo.data.OrderDetailsDTO;

@RestController
@RequestMapping(path = "/api/orders-details")
public class OrderDetailsController {
    private final OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping
    public List<OrderDetailsDTO> FetchOrderDetails() {
        return orderDetailsService.GetOrderDetails();
    }

    @GetMapping(path = "/{orderDetailId}")
    public Optional<OrderDetailsDTO> FetchOrderDetailsById(@PathVariable("orderDetailId") String orderDetailId) {
        return orderDetailsService.GetOrderDetailById(orderDetailId);
    }

    @PostMapping
    public OrderDetailsDTO PostOrderDetails(@RequestBody OrderDetailsDTO orderDetails) {
        return orderDetailsService.CreateOrderDetails(orderDetails);
    }

    @PutMapping(path = "{orderDetailId}")
    public OrderDetailsDTO PutOrderDetails(@PathVariable("orderDetailId") String orderDetailsId,
            @RequestBody OrderDetailsDTO orderDetails) {

        return orderDetailsService.UpdateOrderDetails(orderDetailsId, orderDetails);
    }

    @DeleteMapping(path = "{orderDetailId}")
    public ResponseEntity<String> DeleteOrderDetails(@PathVariable("orderDetailId") String orderDetailsId) {
        orderDetailsService.RemoveOrderDetails(orderDetailsId);

        return ResponseEntity.ok("OrderDetails with ID " + orderDetailsId + " has been deleted.");

    }
}
