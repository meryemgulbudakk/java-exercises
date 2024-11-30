package com.orderstream.orderprocessor.orders.repository;

import com.orderstream.orderprocessor.orders.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
