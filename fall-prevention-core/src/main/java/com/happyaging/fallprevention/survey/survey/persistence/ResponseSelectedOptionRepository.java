package com.happyaging.fallprevention.survey.survey.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.survey.entity.ResponseSelectedOption;

public interface ResponseSelectedOptionRepository extends JpaRepository<ResponseSelectedOption, Long> {
}
