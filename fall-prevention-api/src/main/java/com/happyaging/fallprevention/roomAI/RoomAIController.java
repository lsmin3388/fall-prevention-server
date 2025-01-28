package com.happyaging.fallprevention.roomAI;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.roomAI.usecase.RoomAIUseCase;
import com.happyaging.fallprevention.roomAI.usecase.dto.request.RoomAIRequest;
import com.happyaging.fallprevention.roomAI.usecase.dto.response.RoomAIResponse;
import com.happyaging.fallprevention.senior.MySenior;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roomAI")
@RequiredArgsConstructor
public class RoomAIController {
	private final RoomAIUseCase roomAIUseCase;

	@PostMapping("/{seniorId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<RoomAIResponse>> analyzeRoom(
		@PathVariable("seniorId") @MySenior Long seniorId,
		@Valid @RequestBody List<RoomAIRequest> requestDto
	) {
		RoomAIResponse responseBody = roomAIUseCase.analyzeRoom(seniorId, requestDto);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiResponse.success(HttpStatus.CREATED, responseBody));
	}

	@GetMapping("/date/{seniorId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<List<String>>> getAnalysisDateList(
		@PathVariable("seniorId") @MySenior Long seniorId
	) {
		List<String> responseBody = roomAIUseCase.getAnalysisDateList(seniorId);

		return ResponseEntity
			.ok(ApiResponse.success(HttpStatus.OK, responseBody));
	}

	@GetMapping("/{seniorId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<RoomAIResponse>> getAnalysisResult(
		@PathVariable("seniorId") @MySenior Long seniorId,
		@RequestParam("date") String date
	) {
		RoomAIResponse responseBody = roomAIUseCase.getAnalysisResult(seniorId, date);

		return ResponseEntity
			.ok(ApiResponse.success(HttpStatus.OK, responseBody));
	}
}
