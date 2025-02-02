package com.happyaging.fallprevention.survey.usecase.dto.question;

import java.util.List;

import com.happyaging.fallprevention.product.usecase.dto.ProductQuestionDto;
import com.happyaging.fallprevention.survey.entity.question.QuestionCategory;
import com.happyaging.fallprevention.survey.entity.question.QuestionType;

import lombok.Builder;

@Builder
public record QuestionResponseDto(
         Long id,
         Integer order,
         String content,
         String imageUrl,
         List<QuestionOptionResponseDto> options,
         QuestionCategory questionCategory,
         QuestionType questionType,
         List<ProductQuestionDto> products
) {
}
