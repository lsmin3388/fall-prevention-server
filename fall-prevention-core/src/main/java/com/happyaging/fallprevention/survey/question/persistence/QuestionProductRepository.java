package com.happyaging.fallprevention.survey.question.persistence;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.entity.questionToProduct.QuestionProduct;

public interface QuestionProductRepository extends JpaRepository<QuestionProduct, Long> {
	List<QuestionProduct> findAllByQuestion(Question question);
	List<QuestionProduct> findAllByQuestionIdIn(Set<Long> questionIds);
}
