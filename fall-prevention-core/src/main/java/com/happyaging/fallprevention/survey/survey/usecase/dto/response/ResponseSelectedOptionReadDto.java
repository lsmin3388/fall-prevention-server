
package com.happyaging.fallprevention.survey.survey.usecase.dto.response;

import com.happyaging.fallprevention.survey.question.entity.option.Option;

import lombok.Builder;

@Builder
public record ResponseSelectedOptionReadDto(
	Integer optionNumber,
	String content,
	Integer nextQuestionNumber
) {
	public static ResponseSelectedOptionReadDto of(Option option) {
		Integer nextQ = (option.getNextQuestion() == null)
			? null
			: option.getNextQuestion().getQuestionNumber();

		return ResponseSelectedOptionReadDto.builder()
			.optionNumber(option.getId().getOptionNumber())
			.content(option.getContent())
			.nextQuestionNumber(nextQ)
			.build();
	}
}
