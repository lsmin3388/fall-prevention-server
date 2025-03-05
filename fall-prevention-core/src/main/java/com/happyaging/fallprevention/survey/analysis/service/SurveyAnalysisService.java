package com.happyaging.fallprevention.survey.analysis.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.happyaging.fallprevention.survey.analysis.exception.FailedAnalysisException;
import com.happyaging.fallprevention.survey.analysis.usecase.SurveyAnalysisUseCase;
import com.happyaging.fallprevention.survey.analysis.usecase.dto.AnalysisRequest;
import com.happyaging.fallprevention.survey.analysis.usecase.dto.AnalysisResponse;
import com.happyaging.fallprevention.survey.property.SurveyAnalysisProperties;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyAnalysisService implements SurveyAnalysisUseCase {
	private WebClient webClient;
	private final SurveyAnalysisProperties surveyAnalysisProperties;

	@PostConstruct
	public void init() {
		this.webClient = WebClient.builder()
			.baseUrl(surveyAnalysisProperties.getApiUrl())
			.defaultHeaders(httpHeaders -> {
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			})
			.build();
	}

	@Override
	public AnalysisResponse analyzeSurvey(AnalysisRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		// request를 JSON으로 변환 후 로그 출력
		try {
			String jsonRequest = mapper.writeValueAsString(request);
			log.info("request: \n{}", jsonRequest);
		} catch (Exception e) {
			log.error("Failed to convert request to json: {}", e.getMessage());
		}

		try {
			return this.webClient.post()
				.bodyValue(request)
				.retrieve()
				.bodyToMono(AnalysisResponse.class)
				.onErrorResume(e -> {
					log.error("callGPTApi failed: {}", e.getMessage());
					throw new FailedAnalysisException();
				})
				.block();
		} catch (Exception e) {
			log.error("Failed to analyze survey: {}", e.getMessage());
			throw new FailedAnalysisException();
		}
	}
}
