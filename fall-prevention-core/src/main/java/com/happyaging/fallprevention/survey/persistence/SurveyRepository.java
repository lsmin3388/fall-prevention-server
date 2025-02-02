package com.happyaging.fallprevention.survey.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.survey.entity.survey.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllBySeniorId(Long seniorId);
}
