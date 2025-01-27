package com.happyaging.fallprevention.gpt.dto.response;

import java.util.List;

public record FallRiskAnalysisDTO(
	String imageDescription,
	FallAnalysisDTO fallAnalysis,
	String fallSummaryDescription,
	List<String> fallPreventionMeasures
) {
}
