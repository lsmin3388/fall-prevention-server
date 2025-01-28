package com.happyaging.fallprevention.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyaging.fallprevention.auth.usecase.AuthUseCase;
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
public class LogoutController {

	private final AuthUseCase authUseCase;
	private final JwtTokenService jwtTokenService;
	private final TokenCookieUtil tokenCookieUtil;

	@PostMapping("/logout")
	public ApiSuccessResult<Object> logout(HttpServletRequest request, HttpServletResponse response) {
		String refreshTokenValue = jwtTokenService.resolveRefreshToken(request);

		authUseCase.logout(refreshTokenValue);

		Cookie deleteAccessCookie = tokenCookieUtil.deleteCookieForAccessToken();
		Cookie deleteRefreshCookie = tokenCookieUtil.deleteCookieForRefreshToken();

		response.addCookie(deleteAccessCookie);
		response.addCookie(deleteRefreshCookie);

		return ApiResponse.success(HttpStatus.OK, "LOGOUT_SUCCESS");
	}
}
