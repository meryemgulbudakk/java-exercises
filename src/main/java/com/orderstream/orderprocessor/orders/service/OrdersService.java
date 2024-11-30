package com.orderstream.orderprocessor.orders.service;

import com.orderstream.orderprocessor.orders.model.Orders;
import com.orderstream.orderprocessor.orders.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepo;
    @Autowired
    private ProductService productService;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public void saveOrder(Orders order) {
        executor.submit(() -> {
            try {
                if (order.getCustomer() == null || order.getProduct() == null) {
                    throw new IllegalArgumentException("Customer or product information is missing!");
                }

                productService.updateStock(order.getProduct().getProductId(), order.getQuantity());
                order.setOrderTimestamp(LocalDateTime.now());
                ordersRepo.save(order);
            } catch (Exception e) {
                System.err.println("Order could not be processed: " + e.getMessage());
                throw new RuntimeException("Order could not be processed: " + e.getMessage());
            }
        });
    }

    public List<Orders> getAllOrders() {
        return ordersRepo.findAll();
    }
}
