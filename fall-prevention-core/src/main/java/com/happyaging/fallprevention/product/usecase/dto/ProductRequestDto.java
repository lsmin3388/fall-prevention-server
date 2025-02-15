package com.happyaging.fallprevention.product.usecase.dto;

import com.happyaging.fallprevention.product.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDto(
        @NotBlank String name,
        @NotNull Integer price,
        @NotBlank String description,
        String imageUrl
) {
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .description(description)
                .imageUrl(imageUrl)
                .build();
    }
}
