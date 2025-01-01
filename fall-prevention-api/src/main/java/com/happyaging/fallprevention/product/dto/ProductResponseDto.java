package com.happyaging.fallprevention.product.dto;

import com.happyaging.fallprevention.product.entity.Product;
import lombok.Builder;

@Builder
public record ProductResponseDto(
        Long id,
        String name,
        Integer price,
        String description,
        String imageUrl
) {
    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
