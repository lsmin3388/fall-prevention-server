package com.happyaging.fallprevention.survey.usecase.question;

import java.util.List;

import com.happyaging.fallprevention.survey.entity.question.Question;

public interface QuestionUserUseCase {
    // Read
    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
}
