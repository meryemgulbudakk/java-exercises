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
        System.out.println("Stok kontrol başlatıldı: Ürün ID: " + productId);
        if (lock.tryLock(10, TimeUnit.SECONDS)) {
            try {
                ProductModel productModel = productRepo.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: ürün id" + productId));
                if (productModel.getQuantity() < quantity) {
                    throw new RuntimeException("Yeterli stok yok!, ürün id" + productId);
                }
                productModel.setQuantity(productModel.getQuantity() - quantity);
                productRepo.save(productModel);
                System.out.println("Stok başarıyla güncellendi: Ürün ID: " + productId + ", Yeni stok: " + productModel.getQuantity());

            } finally {
                lock.unlock();
            }
        } else {
            System.err.println("Stok güncelleme işlemi zaman aşımına uğradı: Ürün ID: " + productId);
            throw new RuntimeException("Stok güncelleme işlemi zaman aşımına uğradı!");
        }
    }


}
