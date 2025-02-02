package com.happyaging.fallprevention.product.mapper;

import com.happyaging.fallprevention.product.entity.ProductQuestion;
import com.happyaging.fallprevention.product.usecase.dto.ProductQuestionDto;

public class ProductQuestionMapper {
    public static ProductQuestionDto toDto(ProductQuestion productQuestion) {
        return ProductQuestionDto.builder()
                .id(productQuestion.getProduct().getId())
                .priority(productQuestion.getPriority())
                .build();
    }
}
