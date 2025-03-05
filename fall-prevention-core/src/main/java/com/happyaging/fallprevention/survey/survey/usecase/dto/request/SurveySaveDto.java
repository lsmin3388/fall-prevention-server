package com.happyaging.fallprevention.survey.survey.usecase.dto.request;

import java.util.List;

public record SurveySaveDto(
	List<ResponseSaveDto> responses
) {
}
