package com.happyaging.fallprevention.survey.usecase.dto;

import com.happyaging.fallprevention.survey.entity.question.Question;
import com.happyaging.fallprevention.survey.entity.reponse.Response;

import java.util.List;

public record ResponseSummitDto(
        Long questionId,
        List<Long> questionOptionIds,
        String answerText
) {
    public Response toEntity(Question question) {
        return Response.builder()
                .question(question)
                .answerText(answerText)
                .build();
    }
}
