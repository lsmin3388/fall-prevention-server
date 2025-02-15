package com.happyaging.fallprevention.product.usecase.dto;

public record ProductUpdateRequestDto(
        String name,
        Integer price,
        String description,
        String imageUrl) {

}
