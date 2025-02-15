
package com.happyaging.fallprevention.survey.survey.usecase.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.happyaging.fallprevention.survey.survey.entity.Response;

import lombok.Builder;

@Builder
public record ResponseReadDto(
	Integer questionNumber,
	String questionContent,
	List<ResponseSelectedOptionReadDto> options, // 객관식/참거짓 (nullable)
	String answerText // 주관식 (nullable)
) {
	public static ResponseReadDto of(Response response) {
		return ResponseReadDto.builder()
			.questionNumber(
				response.getQuestion() != null
					? response.getQuestion().getQuestionNumber()
					: null
			)
			.questionContent(
				response.getQuestion() != null
					? response.getQuestion().getContent()
					: null
			)
			.options(
				response.getOptions().stream()
					.map(ro -> ResponseSelectedOptionReadDto.of(ro.getOption()))
					.collect(Collectors.toList())
			)
			.answerText(response.getAnswerText())
			.build();
	}
}
