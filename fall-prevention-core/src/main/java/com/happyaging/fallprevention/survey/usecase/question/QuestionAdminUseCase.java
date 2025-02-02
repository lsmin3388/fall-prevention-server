package com.happyaging.fallprevention.survey.usecase.question;

import com.happyaging.fallprevention.survey.entity.question.Question;
import com.happyaging.fallprevention.survey.usecase.dto.question.QuestionRequestDto;
import com.happyaging.fallprevention.survey.usecase.dto.question.QuestionReorderDto;

import java.util.List;

public interface QuestionAdminUseCase {
    Question createQuestion(QuestionRequestDto questionDto);
    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
    void updateQuestion(Long questionId, Question question);
    Long deleteAndReorderQuestion(Long questionId);
    void reorderQuestions(List<QuestionReorderDto> reorderList);
    Question updateNextQuestionOption(Long currentQuestionId, Long questionOptionId, Long nextQuestionId);
}
