package com.happyaging.fallprevention.auth.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.account.service.AccountService;
import com.happyaging.fallprevention.auth.dto.RegisterRequestDto;
import com.happyaging.fallprevention.region.service.RegionService;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {
	private final AccountService accountService;
	private final RegionService regionService;

	@PostMapping("/register")
	public ResponseEntity<ApiSuccessResult<Object>> register(@RequestBody RegisterRequestDto requestDto) {
		accountService.register(requestDto.toEntity(regionService));
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiResponse.success(HttpStatus.CREATED));
	}
}
