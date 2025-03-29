package com.happyaging.fallprevention.survey.survey.usecase.dto.response;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.happyaging.fallprevention.survey.survey.entity.Survey;

import lombok.Builder;

@Builder
public record SurveyReadDto(
	Long surveyId,
	Long seniorId,

	String pdfUrl,
	Integer riskLevel,
	String summary,
	LocalDateTime createdAt

	// List<ResponseReadDto> responses
) {
	public static SurveyReadDto of(Survey survey) {
		return SurveyReadDto.builder()
			.surveyId(survey.getId())
			.seniorId(survey.getSenior().getId())
			.pdfUrl(survey.getPdfUrl())
			.riskLevel(survey.getRiskLevel() != null ? survey.getRiskLevel().getLevel() : null)
			.summary(survey.getSummary())
			// .responses(
			// 	survey.getResponses().stream()
			// 		.map(ResponseReadDto::of)
			// 		.sorted(Comparator.comparing(ResponseReadDto::questionNumber))
			// 		.collect(Collectors.toList())
			// )
			.build();
	}
}
