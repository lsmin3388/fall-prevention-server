package com.happyaging.fallprevention.survey.analysis.usecase.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.happyaging.fallprevention.survey.survey.entity.Response;

import lombok.Builder;

@Builder
public record AnalysisRequest(
	String name,
	List<AnalysisData> data
) {
	public static AnalysisRequest from(String name, List<Response> responses) {
		List<AnalysisData> data = responses.stream()
			.filter(response ->
				response.getQuestion().getWeight() != null &&
					Math.abs(response.getQuestion().getWeight()) > 1e-10)

			.map(response -> AnalysisData.builder()
				.question(response.getQuestion().getContent())
				.answer(
					response.getAnswerText() != null ?
						response.getAnswerText() :
						response.getOptions().stream()
							.map(ro -> ro.getOption().getContent())
							.collect(Collectors.joining(", "))
				)
				.weight(response.getQuestion().getWeight())
				.build())

			.collect(Collectors.toList());

		return AnalysisRequest.builder()
			.name(name)
			.data(data)
			.build();
	}
}
