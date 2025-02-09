package com.happyaging.fallprevention.survey.question.usecase;

import java.util.List;

import com.happyaging.fallprevention.survey.question.usecase.dto.request.QuestionSaveDto;
import com.happyaging.fallprevention.survey.question.usecase.dto.response.QuestionReadDto;

public interface QuestionUseCase {
    Long createQuestion(QuestionSaveDto questionSaveDto);
    Long updateQuestion(Long questionId, QuestionSaveDto questionSaveDto);
    void deleteQuestion(Long questionId);

    List<QuestionReadDto> getAllQuestions();
    QuestionReadDto getQuestion(Long questionId);

}
