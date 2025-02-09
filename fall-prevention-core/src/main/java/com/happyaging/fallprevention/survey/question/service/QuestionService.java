package com.happyaging.fallprevention.survey.question.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.entity.questionToProduct.QuestionProduct;
import com.happyaging.fallprevention.survey.question.exception.QuestionNotFoundException;
import com.happyaging.fallprevention.survey.question.persistence.QuestionRepository;
import com.happyaging.fallprevention.survey.question.usecase.QuestionUseCase;
import com.happyaging.fallprevention.survey.question.usecase.dto.request.QuestionSaveDto;
import com.happyaging.fallprevention.survey.question.usecase.dto.response.QuestionReadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService implements QuestionUseCase {

	private final QuestionRepository questionRepository;
	private final OptionService optionService;
	private final QuestionProductService questionProductService;

	@Override
	@Transactional
	public Long createQuestion(QuestionSaveDto questionSaveDto) {
		// 1. Question 저장
		Question question = questionRepository.save(questionSaveDto.of());

		// 2. 옵션 저장 (OptionService를 통해)
		optionService.saveOptionsForQuestion(question, questionSaveDto.options());

		// 3. QuestionProduct 저장 (QuestionProductService를 통해)
		questionProductService.saveQuestionProductsForQuestion(question, questionSaveDto.products());

		return question.getId();
	}

	@Override
	@Transactional
	public Long updateQuestion(Long questionId, QuestionSaveDto questionSaveDto) {
		// 1. 기존 Question 조회 (없으면 예외 발생)
		Question question = questionRepository.findById(questionId)
			.orElseThrow(QuestionNotFoundException::new);

		// 2. Question 필드 업데이트 (엔티티 내 update() 메서드를 사용한다고 가정)
		question.update(questionSaveDto);

		// 3. 기존 옵션 및 QuestionProduct 삭제
		optionService.deleteOptionsForQuestion(question);
		questionProductService.deleteQuestionProductsForQuestion(question);

		// 4. 새로운 옵션 및 QuestionProduct 저장
		optionService.saveOptionsForQuestion(question, questionSaveDto.options());
		questionProductService.saveQuestionProductsForQuestion(question, questionSaveDto.products());

		return question.getId();
	}

	@Override
	@Transactional
	public void deleteQuestion(Long questionId) {
		// 1. 삭제할 Question 조회 (없으면 예외 발생)
		Question question = questionRepository.findById(questionId)
			.orElseThrow(QuestionNotFoundException::new);

		// 2. 관련 옵션 및 QuestionProduct 삭제
		optionService.deleteOptionsForQuestion(question);
		questionProductService.deleteQuestionProductsForQuestion(question);

		// 3. Question 삭제
		questionRepository.delete(question);
	}

	@Override
	public List<QuestionReadDto> getAllQuestions() {
		// 1. 전체 Question 조회
		List<Question> questions = questionRepository.findAll();
		if (questions.isEmpty()) {
			return List.of();
		}

		// 2. 전체 질문의 ID를 모아서, 연관된 QuestionProduct를 한 번에 조회 (N+1 문제 최소화)
		Set<Long> questionIds = questions.stream()
			.map(Question::getId)
			.collect(Collectors.toSet());
		List<QuestionProduct> allQuestionProducts = questionProductService.getQuestionProductsByQuestionIds(questionIds);
		var questionProductMap = allQuestionProducts.stream()
			.collect(Collectors.groupingBy(qp -> qp.getQuestion().getId()));

		// 3. 각 Question과 연결된 QuestionProduct 정보를 이용해 DTO 변환
		return questions.stream()
			.map(q -> QuestionReadDto.from(q, questionProductMap.getOrDefault(q.getId(), List.of())))
			.collect(Collectors.toList());
	}

	@Override
	public QuestionReadDto getQuestion(Long questionId) {
		// 1. 단일 Question 조회 (없으면 예외 발생)
		Question question = questionRepository.findById(questionId)
			.orElseThrow(QuestionNotFoundException::new);
		// 2. 해당 Question에 연관된 QuestionProduct 조회
		List<QuestionProduct> questionProducts = questionProductService.getQuestionProductsForQuestion(question);
		// 3. DTO 변환 후 반환
		return QuestionReadDto.from(question, questionProducts);
	}
}
