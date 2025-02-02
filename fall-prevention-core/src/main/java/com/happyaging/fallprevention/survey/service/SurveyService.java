package com.happyaging.fallprevention.survey.service;

import com.happyaging.fallprevention.survey.persistence.SurveyRepository;
import com.happyaging.fallprevention.survey.usecase.SurveyUserUseCase;
import com.happyaging.fallprevention.survey.usecase.dto.SurveySummitDto;
import com.happyaging.fallprevention.survey.usecase.dto.SurveyReadDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService implements SurveyUserUseCase {
    private final SurveyRepository surveyRepository;


    @Override
    @Transactional
    public Long createSurvey(SurveySummitDto surveyCreateDto) {
        return 0L;
    }

    @Override
    public List<SurveyReadDto> getSurveysBySeniorId(Long seniorId) {
        return List.of();
    }

    @Override
    public SurveyReadDto getSurveyById(Long surveyId) {
        return null;
    }

    @Override
    @Transactional
    public Long deleteSurvey(Long surveyId, Long seniorId) {
        return 0L;
    }
}
