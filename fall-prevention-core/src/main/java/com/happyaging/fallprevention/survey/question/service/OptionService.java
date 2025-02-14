package com.happyaging.fallprevention.survey.question.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.entity.option.Option;
import com.happyaging.fallprevention.survey.question.entity.option.OptionId;
import com.happyaging.fallprevention.survey.question.exception.QuestionNotFoundException;
import com.happyaging.fallprevention.survey.question.persistence.OptionRepository;
import com.happyaging.fallprevention.survey.question.persistence.QuestionRepository;
import com.happyaging.fallprevention.survey.question.usecase.dto.request.QuestionOptionSaveDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionService {

	private final OptionRepository optionRepository;
	private final QuestionRepository questionRepository;

	/**
	 * 주어진 Question 엔티티에 대해 DTO 목록을 기반으로 옵션 생성 및 저장
	 */
	@Transactional
	public void saveOptionsForQuestion(Question question, List<QuestionOptionSaveDto> optionDtos) {
		if (optionDtos != null && !optionDtos.isEmpty()) {
			// nextQuestionId가 있는 경우 한 번에 조회
			Set<Long> nextQuestionIds = optionDtos.stream()
				.map(QuestionOptionSaveDto::nextQuestionId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

			Map<Long, Question> nextQuestionMap = new HashMap<>();
			if (!nextQuestionIds.isEmpty()) {
				nextQuestionMap = questionRepository.findAllById(nextQuestionIds).stream()
					.collect(Collectors.toMap(Question::getId, Function.identity()));
			}

			List<Option> options = new ArrayList<>();
			for (QuestionOptionSaveDto dto : optionDtos) {
				Question nextQuestion = null;
				if (dto.nextQuestionId() != null) {
					nextQuestion = Optional.ofNullable(nextQuestionMap.get(dto.nextQuestionId()))
						.orElseThrow(QuestionNotFoundException::new);
				}

				OptionId optionId = OptionId.builder()
					.questionId(question.getId())      // 실제 DB PK
					.optionNumber(dto.optionNumber())
					.build();

				Option option = Option.builder()
					.id(optionId)
					.question(question)
					.content(dto.content())
					.nextQuestion(nextQuestion)
					.build();

				options.add(option);
			}
			optionRepository.saveAll(options);
		}
	}

	/**
	 * 주어진 Question(엔티티)에 연관된 옵션들을 삭제
	 */
	@Transactional
	public void deleteOptionsForQuestion(Question question) {
		if (question.getOptions() != null && !question.getOptions().isEmpty()) {
			optionRepository.deleteAll(question.getOptions());
			question.getOptions().clear();
		}
	}
}
