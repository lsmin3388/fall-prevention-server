package com.happyaging.fallprevention.survey.question.service;

import java.util.*;
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
	 * 주어진 Question에 대해 DTO 목록을 기반으로 질문-제품 연관 엔티티를 생성 및 저장합니다.
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
	 * 주어진 Question에 연관된 모든 QuestionProduct를 삭제합니다.
	 */
	@Transactional
	public void deleteQuestionProductsForQuestion(Question question) {
		List<QuestionProduct> products = questionProductRepository.findAllByQuestion(question);
		if (products != null && !products.isEmpty()) {
			questionProductRepository.deleteAll(products);
		}
	}

	/**
	 * 여러 Question ID에 대한 QuestionProduct를 조회하여 반환합니다.
	 */
	@Transactional(readOnly = true)
	public List<QuestionProduct> getQuestionProductsByQuestionIds(Set<Long> questionIds) {
		return questionProductRepository.findAllByQuestionIdIn(questionIds);
	}

	/**
	 * 단일 Question에 대한 QuestionProduct를 조회하여 반환합니다.
	 */
	@Transactional(readOnly = true)
	public List<QuestionProduct> getQuestionProductsForQuestion(Question question) {
		return questionProductRepository.findAllByQuestion(question);
	}
}
