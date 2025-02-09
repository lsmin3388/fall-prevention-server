package com.happyaging.fallprevention.survey.question.usecase.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.happyaging.fallprevention.survey.question.entity.option.Option;

import lombok.Builder;

@Builder
public record QuestionOptionReadDto(
	Integer optionNumber,

	String content,

	Long nextQuestionId
) {
	public static QuestionOptionReadDto from(Option option) {
		return QuestionOptionReadDto.builder()
			.optionNumber(option.getId().getOptionNumber())
			.content(option.getContent())
			.nextQuestionId(option.getNextQuestion().getId())
			.build();
	}

	public static List<QuestionOptionReadDto> from(List<Option> options) {
		return options.stream()
			.map(QuestionOptionReadDto::from)
			.collect(Collectors.toList());
	}
}
