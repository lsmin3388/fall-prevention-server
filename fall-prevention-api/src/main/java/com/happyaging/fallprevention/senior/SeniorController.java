package com.happyaging.fallprevention.senior;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.annotation.LoggedInUser;
import com.happyaging.fallprevention.security.details.PrincipalDetails;
import com.happyaging.fallprevention.senior.usecase.SeniorAdminUseCase;
import com.happyaging.fallprevention.senior.usecase.SeniorUserUseCase;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorCreateDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorReadDto;
import com.happyaging.fallprevention.senior.usecase.dto.SeniorUpdateDto;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/senior")
@RequiredArgsConstructor
public class SeniorController {
	private final SeniorUserUseCase seniorUserUseCase;
	private final SeniorAdminUseCase seniorAdminUseCase;

	@PostMapping
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<Long>> createSenior(
		@Valid @RequestBody SeniorCreateDto createDto,
		@LoggedInUser PrincipalDetails principalDetails
	) {
		Long responseBody = seniorUserUseCase.createSenior(
			principalDetails.account(),
			createDto
		);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiResponse.success(HttpStatus.CREATED, responseBody));
	}

	@PutMapping("/{seniorId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<Long>> updateSenior(
		@RequestBody SeniorUpdateDto updateDto,
		@PathVariable @MySenior Long seniorId
	) {
		Long responseBody = seniorUserUseCase.updateSenior(seniorId, updateDto);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.CREATED, responseBody));
	}

	@DeleteMapping("/{seniorId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<Object>> deleteSenior(
		@PathVariable Long seniorId,
		@LoggedInUser PrincipalDetails principalDetails
	) {
		seniorUserUseCase.deleteSenior(seniorId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK));
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<List<SeniorReadDto>>> getMySeniors(
		@LoggedInUser PrincipalDetails principalDetails
	) {
		List<SeniorReadDto> responseBody = seniorUserUseCase.getMySeniors(principalDetails.account());

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, responseBody));
	}

	@GetMapping("/me/{seniorId}")
	@PreAuthorize("hasRole('USER') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<SeniorReadDto>> getMySenior(
		@PathVariable @MySenior Long seniorId
	) {
		SeniorReadDto responseBody = seniorUserUseCase.getMySenior(seniorId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, responseBody));
	}

	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
	public ResponseEntity<ApiSuccessResult<List<SeniorReadDto>>> getAllSeniors() {
		List<SeniorReadDto> responseBody = seniorAdminUseCase.getSeniors();

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, responseBody));
	}
}
