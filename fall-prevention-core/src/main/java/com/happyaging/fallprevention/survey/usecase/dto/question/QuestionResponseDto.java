package com.happyaging.fallprevention.survey.usecase.dto.question;

import com.happyaging.fallprevention.product.usecase.dto.ProductQuestionDto;
import com.happyaging.fallprevention.survey.entity.question.QuestionCategory;
import com.happyaging.fallprevention.survey.entity.question.QuestionType;
import lombok.Builder;

import java.util.List;

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
