package com.happyaging.fallprevention.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer price;

    private String imageUrl;

    @Column(nullable = false)
    private String description;

    @Builder
    public Product(String name, Integer price, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }


    public void update(Product updatedProduct) {
        this.name = updatedProduct.getName();
        this.price = updatedProduct.getPrice();
        this.description = updatedProduct.getDescription();
        this.imageUrl = updatedProduct.getImageUrl();
    }
}
