package com.orderstream.orderprocessor.orders.service;

import com.orderstream.orderprocessor.orders.model.OrdersModel;
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

    public void saveOrder(OrdersModel order) {
        executor.submit(() -> {
            try {
                if (order.getCustomer() == null || order.getProduct() == null) {
                    throw new IllegalArgumentException("Müşteri veya ürün bilgisi eksik!");
                }

                productService.updateStock(order.getProduct().getProductId(), order.getQuantity());
                order.setOrderTimestamp(LocalDateTime.now());
                ordersRepo.save(order);
            } catch (Exception e) {
                System.err.println("Sipariş işlenemedi: " + e.getMessage());
                throw new RuntimeException("Sipariş işlenemedi: " + e.getMessage());
            }
        });
    }

    public List<OrdersModel> getAllOrders() {
        return ordersRepo.findAll();
    }
}