package com.happyaging.fallprevention.survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.senior.MySenior;
import com.happyaging.fallprevention.survey.survey.usecase.SurveyUseCase;
import com.happyaging.fallprevention.survey.survey.usecase.dto.request.SurveySaveDto;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
	private final SurveyUseCase surveyUseCase;

	@PostMapping("/{seniorId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<Long>> createSurvey(
		@MySenior @PathVariable("seniorId") Long seniorId,
		@RequestBody SurveySaveDto requestBody
	) {
		Long responseBody = surveyUseCase.createSurvey(seniorId, requestBody);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiResponse.success(HttpStatus.CREATED, responseBody));
	}

}
