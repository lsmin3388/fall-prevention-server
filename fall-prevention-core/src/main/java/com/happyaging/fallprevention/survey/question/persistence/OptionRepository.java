package com.happyaging.fallprevention.survey.question.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.question.entity.option.Option;
import com.happyaging.fallprevention.survey.question.entity.option.OptionId;

public interface OptionRepository extends JpaRepository<Option, OptionId> {
	Optional<Option> findByQuestionQuestionNumberAndIdOptionNumber(Integer questionNumber, Integer optionNumber);
}
