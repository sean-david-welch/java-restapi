package com.example.demo.order;

import java.util.function.Function;

public class OrderDTOMapper implements Function<Order, OrderDTO> {

    @Override
    public OrderDTO apply(Order order) {
        String customerId = (order.getCustomer() != null) ? order.getCustomer().getId() : null;

        return new OrderDTO(order.getId(), order.getOrderDate(), customerId, order.getStatus());
    }

}
