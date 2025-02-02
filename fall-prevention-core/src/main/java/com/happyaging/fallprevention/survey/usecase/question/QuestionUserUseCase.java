package com.happyaging.fallprevention.survey.usecase.question;

import com.happyaging.fallprevention.survey.entity.question.Question;

import java.util.List;

public interface QuestionUserUseCase {
    // Read
    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
}
