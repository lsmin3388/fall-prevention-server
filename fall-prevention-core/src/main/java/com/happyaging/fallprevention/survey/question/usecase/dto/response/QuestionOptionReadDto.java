package com.happyaging.fallprevention.survey.question.usecase.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.happyaging.fallprevention.survey.question.entity.option.Option;

import lombok.Builder;

@Builder
public record QuestionOptionReadDto(
	Integer optionNumber,
	String content,
	Integer nextQuestionNumber
) {
	public static QuestionOptionReadDto from(Option option) {
		Integer nextQ = (option.getNextQuestion() == null)
			? null
			: option.getNextQuestion().getQuestionNumber();
		return QuestionOptionReadDto.builder()
			.optionNumber(option.getId().getOptionNumber())
			.content(option.getContent())
			.nextQuestionNumber(nextQ)
			.build();
	}

	public static List<QuestionOptionReadDto> from(List<Option> options) {
		return options.stream()
			.map(QuestionOptionReadDto::from)
			.collect(Collectors.toList());
	}
}
