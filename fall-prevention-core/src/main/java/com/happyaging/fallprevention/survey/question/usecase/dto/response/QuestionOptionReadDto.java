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
		return QuestionOptionReadDto.builder()
			.optionNumber(option.getId().getOptionNumber())
			.content(option.getContent())
			.nextQuestionNumber(option.getNextQuestion().getQuestionNumber())
			.build();
	}

	public static List<QuestionOptionReadDto> from(List<Option> options) {
		return options.stream()
			.map(QuestionOptionReadDto::from)
			.collect(Collectors.toList());
	}
}
