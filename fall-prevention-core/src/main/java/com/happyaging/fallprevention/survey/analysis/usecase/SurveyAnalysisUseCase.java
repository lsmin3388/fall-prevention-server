package com.happyaging.fallprevention.survey.analysis.usecase;

import com.happyaging.fallprevention.survey.analysis.usecase.dto.AnalysisRequest;
import com.happyaging.fallprevention.survey.analysis.usecase.dto.AnalysisResponse;

public interface SurveyAnalysisUseCase {
	AnalysisResponse analyzeSurvey(AnalysisRequest request);
}
