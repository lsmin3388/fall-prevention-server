package com.happyaging.fallprevention.survey.survey.usecase.dto.request;

import java.util.List;

public record SurveySaveDto(
	// String pdfUrl,
	// Integer riskLevel,  // NONE(0), HIGH(1), MEDIUM(2), LOW(3) 식으로 매핑
	// String summary,
	List<ResponseSaveDto> responses
) {
}
