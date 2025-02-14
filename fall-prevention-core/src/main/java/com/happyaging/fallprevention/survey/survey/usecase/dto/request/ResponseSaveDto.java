package com.happyaging.fallprevention.survey.survey.usecase.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record ResponseSaveDto(
	@NotNull(message = "질문 번호는 필수입니다.")
	Integer questionNumber,

	String answerText, // 주관식 응답
	List<Integer> optionNumber // 객관식 응답
) {
}
