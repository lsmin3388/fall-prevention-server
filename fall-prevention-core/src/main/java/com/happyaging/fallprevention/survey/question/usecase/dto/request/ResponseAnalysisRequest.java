package com.happyaging.fallprevention.survey.question.usecase.dto.request;

import java.util.List;

public record ResponseAnalysisRequest(
	String name,
	List<ResponseList> data
) {
}

record ResponseList(
	String question,
	String answer,
	Double weight
) {
}
