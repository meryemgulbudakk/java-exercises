package com.orderstream.orderprocessor.orders.repository;

import com.orderstream.orderprocessor.orders.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
