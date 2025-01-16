package com.happyaging.fallprevention.survey.usecase;

import com.happyaging.fallprevention.survey.usecase.dto.SurveySummitDto;
import com.happyaging.fallprevention.survey.usecase.dto.SurveyReadDto;

import java.util.List;

public interface SurveyUserUseCase {
    Long createSurvey(SurveySummitDto surveyCreateDto);

    List<SurveyReadDto> getSurveysBySeniorId(Long seniorId);

    SurveyReadDto getSurveyById(Long surveyId);

    Long deleteSurvey(Long surveyId, Long seniorId);
}
