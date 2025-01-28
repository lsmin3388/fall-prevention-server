package com.happyaging.fallprevention.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.auth.usecase.AuthUseCase;
import com.happyaging.fallprevention.auth.usecase.dto.LoginRequestDto;
import com.happyaging.fallprevention.token.dto.JwtTokens;
import com.happyaging.fallprevention.token.util.TokenCookieUtil;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
	private final AuthUseCase authUseCase;
	private final TokenCookieUtil tokenCookieUtil;

	@PostMapping("/login")
	public ResponseEntity<ApiSuccessResult<JwtTokens>> login(
		@Valid @RequestBody LoginRequestDto requestDto,
		HttpServletResponse response
	) {
		JwtTokens jwtTokens = authUseCase.login(requestDto);

		Cookie accessTokenCookie = tokenCookieUtil.createCookieForAccessToken(jwtTokens.accessToken().value());
		Cookie refreshTokenCookie = tokenCookieUtil.createCookieForRefreshToken(jwtTokens.refreshToken().value());

		response.addCookie(accessTokenCookie);
		response.addCookie(refreshTokenCookie);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, jwtTokens));
	}


}
