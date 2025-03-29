package com.happyaging.fallprevention.survey.analysis.usecase.dto;

import lombok.Builder;

@Builder
public record AnalysisData(
	String question,
	String answer,
	Double weight
) {
}
