package com.happyaging.fallprevention.survey.usecase.dto.question;

import com.happyaging.fallprevention.survey.entity.question.Question;
import com.happyaging.fallprevention.survey.entity.question.QuestionOption;
import jakarta.validation.constraints.NotBlank;

public record QuestionOptionRequestDto(
        @NotBlank(message = "선택지 순서를 입력하세요.")
        Integer orderNumber,
        @NotBlank(message = "선택지 내용을 입력하세요.")
        String content,
        Long nextQuestionId
) {
        public QuestionOption toEntity(Question question ,Question nextQuestion) {
                return QuestionOption.builder()
                        .orderNumber(orderNumber)
                        .content(content)
                        .nextQuestion(nextQuestion)
                        .build();
        }

}
