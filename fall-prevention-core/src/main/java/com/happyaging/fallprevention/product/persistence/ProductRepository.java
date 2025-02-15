package com.happyaging.fallprevention.product.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
