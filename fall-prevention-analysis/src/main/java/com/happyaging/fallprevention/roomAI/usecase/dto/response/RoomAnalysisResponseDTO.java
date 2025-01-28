package com.happyaging.fallprevention.roomAI.usecase.dto.response;

import java.util.List;

public record RoomAnalysisResponseDTO(
	String imageDescription,
	FallAnalysisDTO fallAnalysis,
	String fallSummaryDescription,
	List<String> fallPreventionMeasures
) {
	public record FallAnalysisDTO(
		String obstacles,
		String floorCondition,
		String otherFactors
	) {
	}
}
