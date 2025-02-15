package com.happyaging.fallprevention.product.usecase.dto;

import com.happyaging.fallprevention.product.entity.Product;

import lombok.Builder;

@Builder
public record ProductResponseDto(
        Long productId,
        String name,
        Integer price,
        String description,
        String imageUrl
) {
    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
