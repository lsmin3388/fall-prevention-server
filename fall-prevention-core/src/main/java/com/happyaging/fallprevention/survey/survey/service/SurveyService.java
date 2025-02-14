package com.happyaging.fallprevention.survey.survey.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.senior.entity.Senior;
import com.happyaging.fallprevention.senior.exception.SeniorNotFoundException;
import com.happyaging.fallprevention.senior.persistence.SeniorRepository;
import com.happyaging.fallprevention.survey.question.entity.Question;
import com.happyaging.fallprevention.survey.question.entity.option.Option;
import com.happyaging.fallprevention.survey.question.exception.OptionNotFoundException;
import com.happyaging.fallprevention.survey.question.exception.QuestionNotFoundException;
import com.happyaging.fallprevention.survey.question.persistence.OptionRepository;
import com.happyaging.fallprevention.survey.question.persistence.QuestionRepository;
import com.happyaging.fallprevention.survey.survey.entity.Response;
import com.happyaging.fallprevention.survey.survey.entity.ResponseSelectedOption;
import com.happyaging.fallprevention.survey.survey.entity.RiskLevel;
import com.happyaging.fallprevention.survey.survey.entity.Survey;
import com.happyaging.fallprevention.survey.survey.exception.SurveyNotFoundException;
import com.happyaging.fallprevention.survey.survey.persistence.SurveyRepository;
import com.happyaging.fallprevention.survey.survey.usecase.SurveyUseCase;
import com.happyaging.fallprevention.survey.survey.usecase.dto.request.ResponseSaveDto;
import com.happyaging.fallprevention.survey.survey.usecase.dto.request.SurveySaveDto;
import com.happyaging.fallprevention.survey.survey.usecase.dto.response.SurveyReadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurveyService implements SurveyUseCase {

	private final SurveyRepository surveyRepository;
	private final SeniorRepository seniorRepository;
	private final QuestionRepository questionRepository;
	private final OptionRepository optionRepository;

	@Override
	@Transactional
	public Long createSurvey(Long seniorId, SurveySaveDto dto) {
		// 1. Senior 조회
		Senior senior = seniorRepository.findById(seniorId)
			.orElseThrow(SeniorNotFoundException::new);

		// 2. Response 분석

		// 3. Survey 엔티티 생성
		Survey survey = Survey.builder()
			.senior(senior)
			.pdfUrl("https://example.com")
			.riskLevel(RiskLevel.NONE)
			.summary("설문 결과 요약")
			.build();

		// 4. Response(응답) 생성
		List<Response> responses = buildResponses(survey, dto.responses());
		survey.getResponses().addAll(responses);

		// 5. Survey 저장
		surveyRepository.save(survey);

		return survey.getId();
	}

	@Override
	public SurveyReadDto getSurvey(Long seniorId, Long surveyId) {
		Survey survey = surveyRepository.findSurveyByIdWithAllRelations(surveyId)
			.orElseThrow(SurveyNotFoundException::new);
		return SurveyReadDto.of(survey);
	}

	@Override
	public List<SurveyReadDto> getSurveysBySenior(Long seniorId) {
		List<Survey> surveys = surveyRepository.findAllWithAllRelations();
		if (surveys.isEmpty()) {
			return List.of();
		}
		// seniorId가 일치하는 설문만 필터링
		return surveys.stream()
			.filter(survey -> survey.getSenior().getId().equals(seniorId))
			.map(SurveyReadDto::of)
			.collect(Collectors.toList());
	}


	@Override
	@Transactional
	public void deleteSurvey(Long surveyId) {
		Survey survey = surveyRepository.findById(surveyId)
			.orElseThrow(SurveyNotFoundException::new);
		surveyRepository.delete(survey);
	}

	private List<Response> buildResponses(Survey survey, List<ResponseSaveDto> responseDtos) {
		List<Response> responses = new ArrayList<>();
		if (responseDtos == null || responseDtos.isEmpty()) {
			return responses;
		}

		for (ResponseSaveDto rDto : responseDtos) {
			Question question = questionRepository.findByQuestionNumber(rDto.questionNumber())
				.orElseThrow(QuestionNotFoundException::new);

			Response response = Response.builder()
				.survey(survey)
				.question(question)
				.answerText(rDto.answerText())
				.build();

			// 선택된 옵션이 있다면
			if (rDto.optionNumber() != null && !rDto.optionNumber().isEmpty()) {
				List<ResponseSelectedOption> selectedOptions = new ArrayList<>();
				for (Integer optionNumber : rDto.optionNumber()) {
					Option option = optionRepository.findByQuestionQuestionNumberAndIdOptionNumber(question.getQuestionNumber(), optionNumber)
						.orElseThrow(OptionNotFoundException::new);
					// ResponseSelectedOption 빌드
					ResponseSelectedOption rso = ResponseSelectedOption.builder()
						.response(response)
						.option(option)
						.build();
					selectedOptions.add(rso);
				}
				response.getOptions().addAll(selectedOptions);
			}

			responses.add(response);
		}
		return responses;
	}
}

