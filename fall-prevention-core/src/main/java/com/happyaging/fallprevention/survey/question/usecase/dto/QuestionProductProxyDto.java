package com.happyaging.fallprevention.survey.question.usecase.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.happyaging.fallprevention.product.entity.Product;
import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.entity.questionToProduct.QuestionProduct;

import lombok.Builder;

@Builder
public record QuestionProductProxyDto(
	Long productId, // productId
	Integer priority
) {
	public QuestionProduct toQuestionProductEntity(Question question, Product product) {
		return QuestionProduct.builder()
			.product(product)
			.question(question)
			.priority(priority)
			.build();
	}

	public static QuestionProductProxyDto from(QuestionProduct questionProduct) {
		return QuestionProductProxyDto.builder()
			.productId(questionProduct.getProduct().getId())
			.priority(questionProduct.getPriority())
			.build();
	}

	public static List<QuestionProductProxyDto> from(List<QuestionProduct> questionProducts) {
		return questionProducts.stream()
			.map(QuestionProductProxyDto::from)
			.collect(Collectors.toList());
	}
}
