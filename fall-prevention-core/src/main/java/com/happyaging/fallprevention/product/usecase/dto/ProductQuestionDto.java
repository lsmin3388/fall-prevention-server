package com.happyaging.fallprevention.product.usecase.dto;

import lombok.Builder;

@Builder
public record ProductQuestionDto(
    Long id,
    Integer priority
) {
}
