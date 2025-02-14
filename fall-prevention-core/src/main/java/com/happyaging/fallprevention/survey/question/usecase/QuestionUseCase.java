package com.happyaging.fallprevention.survey.question.usecase;

import java.util.List;

import com.happyaging.fallprevention.survey.question.usecase.dto.request.QuestionSaveDto;
import com.happyaging.fallprevention.survey.question.usecase.dto.response.QuestionReadDto;

public interface QuestionUseCase {
    Integer createQuestion(QuestionSaveDto questionSaveDto);
    Integer updateQuestion(Integer questionNumber, QuestionSaveDto questionSaveDto);
    void deleteQuestion(Integer questionNumber);

    List<QuestionReadDto> getAllQuestions();
    QuestionReadDto getQuestion(Integer questionNumber);

}
