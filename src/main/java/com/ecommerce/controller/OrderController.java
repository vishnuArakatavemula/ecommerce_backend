package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create Order from Cart
    @PostMapping("/create/{userId}")
    public ResponseEntity<Order> createOrder(@PathVariable Long userId) {

        Order order = orderService.createOrder(userId);

        return ResponseEntity.ok(order);
    }
}