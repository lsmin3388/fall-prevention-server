package com.happyaging.fallprevention.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.auth.usecase.AuthUseCase;
import com.happyaging.fallprevention.token.dto.JwtTokens;
import com.happyaging.fallprevention.token.service.JwtTokenService;
import com.happyaging.fallprevention.token.util.TokenCookieUtil;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RefreshController {
	private final AuthUseCase authUseCase;
	private final TokenCookieUtil tokenCookieUtil;
	private final JwtTokenService jwtTokenService;

	@PostMapping("/refresh")
	public ResponseEntity<ApiSuccessResult<JwtTokens>> refresh(
		HttpServletRequest request, HttpServletResponse response
	) {
		String refreshTokenValue = jwtTokenService.resolveRefreshToken(request);
		JwtTokens newTokens = authUseCase.refresh(refreshTokenValue);

		Cookie accessTokenCookie = tokenCookieUtil.createCookieForAccessToken(newTokens.accessToken().value());
		Cookie refreshTokenCookie = tokenCookieUtil.createCookieForRefreshToken(newTokens.refreshToken().value());

		response.addCookie(accessTokenCookie);

		response.addCookie(refreshTokenCookie);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, newTokens));
	}

}
