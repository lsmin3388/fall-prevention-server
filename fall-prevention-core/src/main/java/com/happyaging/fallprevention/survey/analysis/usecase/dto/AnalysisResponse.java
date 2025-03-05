package com.happyaging.fallprevention.survey.analysis.usecase.dto;

import java.util.List;

public record AnalysisResponse(
	Integer rank,
	String report,
	String summary,
	List<AnalysisProductResponse> product
) {
}
