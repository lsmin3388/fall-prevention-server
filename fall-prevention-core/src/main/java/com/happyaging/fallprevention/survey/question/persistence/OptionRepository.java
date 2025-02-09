package com.happyaging.fallprevention.survey.question.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.question.entity.option.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
