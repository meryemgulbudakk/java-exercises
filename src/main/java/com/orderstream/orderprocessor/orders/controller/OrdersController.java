package com.orderstream.orderprocessor.orders.controller;

import com.orderstream.orderprocessor.orders.model.OrdersModel;
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
    public String saveOrder(@RequestBody OrdersModel ordersModel) {
        try {
            service.saveOrder(ordersModel);
            return "Order saved successfully";
        } catch (RuntimeException e) {
            return "Order failed: " + e.getMessage();
        }
    }

    @GetMapping
    public List<OrdersModel> getAllOrders() {
        return service.getAllOrders();
    }
}
