package com.orderstream.orderprocessor.orders.repository;

import com.orderstream.orderprocessor.orders.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
}
