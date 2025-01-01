package com.happyaging.fallprevention.product.dto;

public record ProductUpdateRequestDto(
        String name,
        Integer price,
        String description,
        String imageUrl) {

}
