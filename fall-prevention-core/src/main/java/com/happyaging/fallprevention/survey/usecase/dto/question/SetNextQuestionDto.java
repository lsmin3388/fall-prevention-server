package com.happyaging.fallprevention.survey.usecase.dto.question;

public record SetNextQuestionDto(
        Long currentQuestionId,
        Long selectedOptionId,
        Long nextQuestionId
) {
}
