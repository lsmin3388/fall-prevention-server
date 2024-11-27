package com.happyaging.fallprevention.auth.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.auth.dto.LoginRequestDto;
import com.happyaging.fallprevention.token.dto.JwtTokens;
import com.happyaging.fallprevention.token.service.AuthenticationService;
import com.happyaging.fallprevention.util.api.ApiResponse;
import com.happyaging.fallprevention.util.api.ApiSuccessResult;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
	private final AuthenticationManager authenticationManager;
	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<ApiSuccessResult<JwtTokens>> register(
		@RequestBody LoginRequestDto requestDto,
		HttpServletResponse response
	) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
		);

		JwtTokens jwtTokens = authenticationService.issueToken(authentication);

		Cookie accessTokenCookie = authenticationService.createCookie(jwtTokens.accessToken());
		Cookie refreshTokenCookie = authenticationService.createCookie(jwtTokens.refreshToken());

		response.addCookie(accessTokenCookie);
		response.addCookie(refreshTokenCookie);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(HttpStatus.OK, jwtTokens));
	}
}
