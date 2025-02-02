package com.happyaging.fallprevention.survey.usecase;

import java.util.List;

import com.happyaging.fallprevention.survey.usecase.dto.SurveyReadDto;
import com.happyaging.fallprevention.survey.usecase.dto.SurveySummitDto;

public interface SurveyUserUseCase {
    Long createSurvey(SurveySummitDto surveyCreateDto);

    List<SurveyReadDto> getSurveysBySeniorId(Long seniorId);

    SurveyReadDto getSurveyById(Long surveyId);

    Long deleteSurvey(Long surveyId, Long seniorId);
}
