package com.happyaging.fallprevention.product.entity;

import com.happyaging.fallprevention.product.usecase.dto.ProductUpdateRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(nullable = false)
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


    public void update(ProductUpdateRequestDto updatedProduct) {
        if (updatedProduct.name() != null)
            this.name = updatedProduct.name();

        if (updatedProduct.price() != null)
            this.price = updatedProduct.price();

        if (updatedProduct.description() != null)
            this.description = updatedProduct.description();

        if (updatedProduct.imageUrl() != null)
            this.imageUrl = updatedProduct.imageUrl();
    }
}
