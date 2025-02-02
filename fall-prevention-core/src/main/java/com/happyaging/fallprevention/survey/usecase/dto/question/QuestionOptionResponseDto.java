package com.happyaging.fallprevention.survey.usecase.dto.question;

import lombok.Builder;

@Builder
public record QuestionOptionResponseDto(
        Long id,
        Integer order,
        String content,
        Long nextQuestionId
) {
}
