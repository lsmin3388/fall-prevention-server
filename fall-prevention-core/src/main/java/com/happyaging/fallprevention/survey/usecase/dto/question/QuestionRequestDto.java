package com.happyaging.fallprevention.survey.usecase.dto.question;

import com.happyaging.fallprevention.product.usecase.dto.ProductQuestionDto;
import com.happyaging.fallprevention.survey.entity.question.Question;
import com.happyaging.fallprevention.survey.entity.question.QuestionCategory;
import com.happyaging.fallprevention.survey.entity.question.QuestionType;
import io.micrometer.common.KeyValues;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.util.List;

public record QuestionRequestDto(
        @NotBlank(message = "질문 내용을 입력하세요.")
        String content,
        String imageUrl,
        List<QuestionOptionRequestDto> options,
        @NotNull(message = "질문 종류를 선택하세요.")
        QuestionCategory questionCategory,
        @NotNull(message = "질문 방식을 선택하세요.")
        QuestionType questionType,
        List<ProductQuestionDto> products
) {
        public Question toEntity() {
                return Question.builder()
                        .content(content)
                        .imageUrl(imageUrl)
                        .category(questionCategory)
                        .type(questionType)
                        .build();
        }

}
