package com.happyaging.fallprevention.survey.question.persistence;

import org.springframework.stereotype.Repository;

import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.exception.QuestionNotFoundException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuestionDao {
	private final QuestionRepository questionRepository;

	public Question getQuestionById(Long questionId) {
		return questionRepository.findById(questionId).orElseThrow(
			QuestionNotFoundException::new);
	}
}
