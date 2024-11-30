package com.orderstream.orderprocessor.orders.controller;

import com.orderstream.orderprocessor.orders.model.Orders;
import com.orderstream.orderprocessor.orders.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService service;

    @PostMapping("/save")
    public String saveOrder(@RequestBody Orders orders) {
        try {
            service.saveOrder(orders);
            return "Order saved successfully";
        } catch (RuntimeException e) {
            return "Order failed: " + e.getMessage();
        }
    }

    @GetMapping
    public List<Orders> getAllOrders() {
        return service.getAllOrders();
    }
}
