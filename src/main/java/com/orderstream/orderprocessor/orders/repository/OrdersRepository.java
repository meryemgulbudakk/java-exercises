package com.orderstream.orderprocessor.orders.repository;

import com.orderstream.orderprocessor.orders.model.OrdersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<OrdersModel, Long> {

}
