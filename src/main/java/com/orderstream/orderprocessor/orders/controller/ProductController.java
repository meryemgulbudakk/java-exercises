package com.orderstream.orderprocessor.orders.controller;

import com.orderstream.orderprocessor.orders.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/update-stock")
    public String updateStock(@RequestParam Long productId, @RequestParam int quantity) throws InterruptedException {
        productService.updateStock(productId, quantity);
        return "Stock updated successfully";
    }
}