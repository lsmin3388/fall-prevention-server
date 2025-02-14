package com.happyaging.fallprevention.survey.survey.usecase;

import java.util.List;

import com.happyaging.fallprevention.survey.survey.usecase.dto.request.SurveySaveDto;
import com.happyaging.fallprevention.survey.survey.usecase.dto.response.SurveyReadDto;

public interface SurveyUseCase {
	Long createSurvey(Long seniorId, SurveySaveDto dto);
	SurveyReadDto getSurvey(Long seniorId, Long surveyId);
	List<SurveyReadDto> getSurveysBySenior(Long seniorId);
	void deleteSurvey(Long surveyId);
}
