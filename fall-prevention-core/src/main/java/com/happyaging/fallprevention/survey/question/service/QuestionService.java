package com.happyaging.fallprevention.survey.question.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.entity.questionToProduct.QuestionProduct;
import com.happyaging.fallprevention.survey.question.exception.QuestionDuplicatedException;
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

	/**
	 * 1) Question 생성
	 * 2) Option 저장
	 * 3) QuestionProduct 저장
	 */
	@Override
	@Transactional
	public Integer createQuestion(QuestionSaveDto questionSaveDto) {
		if (questionRepository.existsByQuestionNumber(questionSaveDto.questionNumber())) {
			throw new QuestionDuplicatedException();
		}

		// Question 엔티티 생성 & 저장
		Question question = questionRepository.save(questionSaveDto.of());

		// Option 저장
		optionService.saveOptionsForQuestion(question, questionSaveDto.options());

		// QuestionProduct 저장
		questionProductService.saveQuestionProductsForQuestion(question, questionSaveDto.products());

		// 여기서는 questionNumber를 반환
		return question.getQuestionNumber();
	}

	/**
	 * questionNumber로 Question 조회 후 수정
	 */
	@Override
	@Transactional
	public Integer updateQuestion(Integer questionNumber, QuestionSaveDto questionSaveDto) {
		// questionNumber가 변경되었을 때 중복 체크
		if (!Objects.equals(questionNumber, questionSaveDto.questionNumber())
			&& questionRepository.existsByQuestionNumber(questionSaveDto.questionNumber())) {
			throw new QuestionDuplicatedException();
		}

		// PK 대신 questionNumber 로 조회
		Question question = questionRepository.findByQuestionNumber(questionNumber)
			.orElseThrow(QuestionNotFoundException::new);

		// 필드 업데이트
		question.update(questionSaveDto);

		// 옵션/연관상품 삭제 후 다시 등록
		optionService.deleteOptionsForQuestion(question);
		questionProductService.deleteQuestionProductsForQuestion(question);

		optionService.saveOptionsForQuestion(question, questionSaveDto.options());
		questionProductService.saveQuestionProductsForQuestion(question, questionSaveDto.products());

		// 변경된 questionNumber 반환 (update 시에 questionNumber도 변경될 수 있으므로)
		return question.getQuestionNumber();
	}

	/**
	 * questionNumber로 Question 조회 후 삭제
	 */
	@Override
	@Transactional
	public void deleteQuestion(Integer questionNumber) {
		Question question = questionRepository.findByQuestionNumber(questionNumber)
			.orElseThrow(QuestionNotFoundException::new);

		optionService.deleteOptionsForQuestion(question);
		questionProductService.deleteQuestionProductsForQuestion(question);

		questionRepository.delete(question);
	}

	/**
	 * questionNumber ASC 정렬로 모든 Question 조회
	 */
	@Override
	public List<QuestionReadDto> getAllQuestions() {
		// questionNumber 오름차순으로 전체 조회
		List<Question> questions = questionRepository.findAllByOrderByQuestionNumberAsc();
		if (questions.isEmpty()) {
			return List.of();
		}

		// 모든 questionNumber만 추출
		Set<Integer> questionNumbers = questions.stream()
			.map(Question::getQuestionNumber)
			.collect(Collectors.toSet());

		// 연관된 QuestionProduct를 questionNumber 기준으로 한 번에 조회
		List<QuestionProduct> allQuestionProducts =
			questionProductService.getQuestionProductsByQuestionNumbers(questionNumbers);

		// QuestionNumber -> QuestionProduct 목록 매핑
		Map<Integer, List<QuestionProduct>> questionProductMap =
			allQuestionProducts.stream()
				.collect(Collectors.groupingBy(qp -> qp.getQuestion().getQuestionNumber()));

		// DTO 변환
		return questions.stream()
			.map(q -> {
				List<QuestionProduct> products =
					questionProductMap.getOrDefault(q.getQuestionNumber(), List.of());
				return QuestionReadDto.from(q, products);
			})
			.collect(Collectors.toList());
	}

	/**
	 * questionNumber로 단일 Question 조회
	 */
	@Override
	public QuestionReadDto getQuestion(Integer questionNumber) {
		// questionNumber로 조회
		Question question = questionRepository.findByQuestionNumber(questionNumber)
			.orElseThrow(QuestionNotFoundException::new);

		// 해당 Question에 연관된 QuestionProduct 조회
		List<QuestionProduct> questionProducts =
			questionProductService.getQuestionProductsForQuestion(questionNumber);

		return QuestionReadDto.from(question, questionProducts);
	}
}
