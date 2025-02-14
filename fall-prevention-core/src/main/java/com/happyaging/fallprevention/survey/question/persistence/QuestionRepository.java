package com.happyaging.fallprevention.survey.question.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findAllByOrderByQuestionNumberAsc();
	Optional<Question> findByQuestionNumber(Integer questionNumber);
}
