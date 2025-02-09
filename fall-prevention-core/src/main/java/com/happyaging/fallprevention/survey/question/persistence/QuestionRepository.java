package com.happyaging.fallprevention.survey.question.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
