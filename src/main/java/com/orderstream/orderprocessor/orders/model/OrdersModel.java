package com.orderstream.orderprocessor.orders.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
public class OrdersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customerModel;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel productModel;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime orderTimestamp;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public CustomerModel getCustomer() {
        return customerModel;
    }

    public void setCustomer(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public ProductModel getProduct() {
        return productModel;
    }

    public void setProduct(ProductModel productModel) {
        this.productModel = productModel;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(LocalDateTime orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
}
