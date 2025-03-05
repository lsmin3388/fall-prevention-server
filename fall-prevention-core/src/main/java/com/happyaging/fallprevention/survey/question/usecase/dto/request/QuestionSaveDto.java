package com.happyaging.fallprevention.survey.question.usecase.dto.request;

import java.util.List;

import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.entity.QuestionCategory;
import com.happyaging.fallprevention.survey.question.entity.QuestionType;
import com.happyaging.fallprevention.survey.question.usecase.dto.QuestionProductProxyDto;

import jakarta.validation.constraints.NotNull;

/*
 * 질문 생성 및 수정 시 사용하는 DTO
 */
public record QuestionSaveDto(
	Integer questionNumber,

	String content, // 질문 내용

	String imageUrl, // 이미지 URL

	Double weight, // 질문 가중치

	@NotNull(message = "질문 종류를 선택하세요.")
	QuestionCategory category, // 질문 카테고리 (질변 관련, 몸 상태, 운동 관련, 낙상 경험, 주거 환경)

	@NotNull(message = "질문 방식을 선택하세요.")
	QuestionType type, // 질문 타입 (객관식, 주관식, 참거짓)

	List<QuestionOptionSaveDto> options, // 객관식일 때 선택지 목록

	List<QuestionProductProxyDto> products // 질문과 연결된 제품 목록
) {
	public Question of() {
		return Question.builder()
			.questionNumber(questionNumber)
			.content(content)
			.imageUrl(imageUrl)
			.questionCategory(category)
			.questionType(type)
			.weight(weight)
			.build();
	}
}
