package com.happyaging.fallprevention.survey.question.usecase.dto.response;

import java.util.List;

import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.entity.questionToProduct.QuestionProduct;
import com.happyaging.fallprevention.survey.question.usecase.dto.QuestionProductProxyDto;

import lombok.Builder;

@Builder
public record QuestionReadDto(
	Integer questionNumber,
	String content,
	String imageUrl,
	String category,
	String type,

	List<QuestionOptionReadDto> options,
	List<QuestionProductProxyDto> products
) {
	public static QuestionReadDto from(Question question, List<QuestionProduct> products) {
		return QuestionReadDto.builder()
			.questionNumber(question.getQuestionNumber())
			.content(question.getContent())
			.imageUrl(question.getImageUrl())
			.category(question.getQuestionCategory().getCategory())
			.type(question.getQuestionType().getType())
			.options(QuestionOptionReadDto.from(question.getOptions()))
			.products(QuestionProductProxyDto.from(products))
			.build();
	}
}
