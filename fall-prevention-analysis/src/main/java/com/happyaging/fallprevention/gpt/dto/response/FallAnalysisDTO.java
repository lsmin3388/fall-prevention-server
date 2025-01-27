package com.happyaging.fallprevention.gpt.dto.response;

public record FallAnalysisDTO(
	String obstacles,
	String floorCondition,
	String otherFactors
) {
}
