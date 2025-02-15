package com.happyaging.fallprevention.survey;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.senior.MySenior;
import com.happyaging.fallprevention.survey.survey.usecase.SurveyUseCase;
import com.happyaging.fallprevention.survey.survey.usecase.dto.request.SurveySaveDto;
import com.happyaging.fallprevention.survey.survey.usecase.dto.response.SurveyReadDto;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {

	private final SurveyUseCase surveyUseCase;

	/**
	 * 설문 생성: POST /survey/{seniorId}
	 */
	@PostMapping("/{seniorId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<Long>> createSurvey(
		@MySenior @PathVariable("seniorId") Long seniorId,
		@RequestBody SurveySaveDto requestBody
	) {
		Long surveyId = surveyUseCase.createSurvey(seniorId, requestBody);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiResponse.success(HttpStatus.CREATED, surveyId));
	}

	/**
	 * 단일 설문 조회: GET /survey/{seniorId}/{surveyId}
	 */
	@GetMapping("/{seniorId}/{surveyId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<SurveyReadDto>> getSurvey(
		@MySenior @PathVariable("seniorId") Long seniorId,
		@PathVariable("surveyId") Long surveyId
	) {
		SurveyReadDto survey = surveyUseCase.getSurvey(seniorId, surveyId);
		return ResponseEntity
			.ok(ApiResponse.success(HttpStatus.OK, survey));
	}

	/**
	 * 특정 어르신의 전체 설문 조회: GET /survey/senior/{seniorId}
	 */
	@GetMapping("/senior/{seniorId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<List<SurveyReadDto>>> getSurveysBySenior(
		@MySenior @PathVariable("seniorId") Long seniorId
	) {
		List<SurveyReadDto> surveys = surveyUseCase.getSurveysBySenior(seniorId);
		return ResponseEntity
			.ok(ApiResponse.success(HttpStatus.OK, surveys));
	}

	/**
	 * 설문 삭제: DELETE /survey/{surveyId}
	 */
	@DeleteMapping("/{surveyId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<Void>> deleteSurvey(
		@PathVariable("surveyId") Long surveyId
	) {
		surveyUseCase.deleteSurvey(surveyId);
		return ResponseEntity
			.ok(ApiResponse.success(HttpStatus.OK, null));
	}
}
