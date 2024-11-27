package com.orderstream.orderprocessor.orders.repository;

import com.orderstream.orderprocessor.orders.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
