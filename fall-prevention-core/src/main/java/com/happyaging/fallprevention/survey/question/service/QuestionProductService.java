package com.happyaging.fallprevention.survey.question.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.product.entity.Product;
import com.happyaging.fallprevention.product.exception.ProductNotFoundException;
import com.happyaging.fallprevention.product.persistence.ProductRepository;
import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.entity.questionToProduct.QuestionProduct;
import com.happyaging.fallprevention.survey.question.persistence.QuestionProductRepository;
import com.happyaging.fallprevention.survey.question.usecase.dto.QuestionProductProxyDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionProductService {

	private final QuestionProductRepository questionProductRepository;
	private final ProductRepository productRepository;

	/**
	 * 주어진 Question에 대해 DTO 목록을 기반으로 질문-제품 연관 엔티티 생성 및 저장
	 */
	@Transactional
	public void saveQuestionProductsForQuestion(Question question, List<QuestionProductProxyDto> productDtos) {
		if (productDtos != null && !productDtos.isEmpty()) {
			// DTO에 담긴 productId들을 한 번에 조회
			Set<Long> productIds = productDtos.stream()
				.map(QuestionProductProxyDto::productId)
				.collect(Collectors.toSet());
			Map<Long, Product> productMap = productRepository.findAllById(productIds)
				.stream()
				.collect(Collectors.toMap(Product::getId, Function.identity()));

			List<QuestionProduct> questionProducts = new ArrayList<>();
			for (QuestionProductProxyDto dto : productDtos) {
				Product product = Optional.ofNullable(productMap.get(dto.productId()))
					.orElseThrow(ProductNotFoundException::new);
				QuestionProduct qp = dto.toQuestionProductEntity(question, product);
				questionProducts.add(qp);
			}
			questionProductRepository.saveAll(questionProducts);
		}
	}

	/**
	 * 주어진 Question(객체)에 연관된 QuestionProduct 삭제
	 */
	@Transactional
	public void deleteQuestionProductsForQuestion(Question question) {
		// questionNumber로 찾을 수도 있지만, 여기서는 이미 question 엔티티가 있으므로
		List<QuestionProduct> products = questionProductRepository
			.findAllByQuestionQuestionNumber(question.getQuestionNumber());
		if (products != null && !products.isEmpty()) {
			questionProductRepository.deleteAll(products);
		}
	}

	/**
	 * 여러 questionNumber에 대한 QuestionProduct 목록 조회
	 */
	public List<QuestionProduct> getQuestionProductsByQuestionNumbers(Set<Integer> questionNumbers) {
		if (questionNumbers == null || questionNumbers.isEmpty()) {
			return List.of();
		}
		return questionProductRepository.findAllByQuestionQuestionNumberIn(questionNumbers);
	}

	/**
	 * 단일 questionNumber에 대한 QuestionProduct 조회
	 */
	public List<QuestionProduct> getQuestionProductsForQuestion(Integer questionNumber) {
		return questionProductRepository.findAllByQuestionQuestionNumber(questionNumber);
	}
}
