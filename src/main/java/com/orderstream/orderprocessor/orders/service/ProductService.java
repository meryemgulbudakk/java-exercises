package com.orderstream.orderprocessor.orders.service;

import com.orderstream.orderprocessor.orders.model.ProductModel;
import com.orderstream.orderprocessor.orders.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;
    private final ReentrantLock lock = new ReentrantLock();

    public void updateStock(Long productId, int quantity) throws InterruptedException {
        System.out.println("Stock check initiated: Product ID: " + productId);
        if (lock.tryLock(10, TimeUnit.SECONDS)) {
            try {
                ProductModel productModel = productRepo.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found: Product ID" + productId));
                if (productModel.getQuantity() < quantity) {
                    throw new RuntimeException("Insufficient stock!, Product ID" + productId);
                }
                productModel.setQuantity(productModel.getQuantity() - quantity);
                productRepo.save(productModel);
                System.out.println("Stock updated successfully: Product ID: " + productId + ", New stock: " + productModel.getQuantity());

            } finally {
                lock.unlock();
            }
        } else {
            System.err.println("Stock update operation timed out: Product ID: " + productId);
            throw new RuntimeException("Stock update operation timed out!");
        }
    }


}
