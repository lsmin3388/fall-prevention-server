package com.happyaging.fallprevention.survey.persistence;

import com.happyaging.fallprevention.survey.entity.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllBySeniorId(Long seniorId);
}
