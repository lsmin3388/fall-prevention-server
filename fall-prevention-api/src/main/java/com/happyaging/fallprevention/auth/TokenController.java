package com.happyaging.fallprevention.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.auth.usecase.AuthUseCase;
import com.happyaging.fallprevention.token.dto.JwtTokens;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TokenController {
	private final AuthUseCase authUseCase;

	@PostMapping("/refresh")
	public ResponseEntity<ApiSuccessResult<JwtTokens>> refresh(HttpServletRequest request, HttpServletResponse response) {
		JwtTokens newTokens = authUseCase.refresh(request, response);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, newTokens));
	}
}
