package com.happyaging.fallprevention.survey.usecase;

import java.util.List;

import com.happyaging.fallprevention.survey.entity.question.Question;
import com.happyaging.fallprevention.survey.entity.reponse.Response;
import com.happyaging.fallprevention.survey.usecase.dto.ResponseSummitDto;

public interface ResponseUseCase {
    Response createResponse(Response response);
    Response getResponseById(Long id);
    List<Response> getAllResponses();
    Response getResponseByQuestion(List<Response> responses, Question question);
    List<Response> convertAndSaveAll(List<ResponseSummitDto> responseSummitDtoList);
}
