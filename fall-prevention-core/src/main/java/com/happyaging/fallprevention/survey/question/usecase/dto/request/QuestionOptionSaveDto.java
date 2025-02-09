package com.happyaging.fallprevention.survey.question.usecase.dto.request;

import jakarta.validation.constraints.NotBlank;

public record QuestionOptionSaveDto(

	@NotBlank(message = "선택지 순서를 입력하세요.")
	Integer optionNumber,

	@NotBlank(message = "선택지 내용을 입력하세요.")
	String content,

	Long nextQuestionId
) {
}

