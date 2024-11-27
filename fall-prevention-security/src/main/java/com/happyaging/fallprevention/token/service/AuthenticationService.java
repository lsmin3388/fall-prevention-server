package com.happyaging.fallprevention.token.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.happyaging.fallprevention.properties.JwtProperties;
import com.happyaging.fallprevention.security.details.PrincipalDetails;
import com.happyaging.fallprevention.token.dto.JwtTokens;
import com.happyaging.fallprevention.token.dto.Token;
import com.happyaging.fallprevention.token.dto.TokenType;
import com.happyaging.fallprevention.token.entity.RefreshToken;
import com.happyaging.fallprevention.util.cookie.CookieUtil;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final JwtProperties jwtProperties;
	private final JwtTokenService jwtTokenService;
	private final RefreshTokenService refreshTokenService;

	public JwtTokens issueToken(Authentication authentication) {
		Token accessToken = jwtTokenService.generateToken(authentication, TokenType.ACCESS_TOKEN);
		Token refreshToken = jwtTokenService.generateToken(authentication, TokenType.REFRESH_TOKEN);

		String email = ((PrincipalDetails) authentication.getPrincipal()).getUsername();
		refreshTokenService.updateRefreshToken(RefreshToken.builder()
			.email(email)
			.refreshToken(refreshToken.value())
			.build()
		);

		return JwtTokens.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public Cookie createCookie(Token token) {
		String cookieName = token.tokenType().name();
		String tokenValue = token.value();
		Integer expiration = jwtProperties.getExpiration(token.tokenType());
		String path = "/";
		boolean httpOnly = token.tokenType() == TokenType.REFRESH_TOKEN;

		return CookieUtil.createCookie(
			cookieName,
			tokenValue,
			expiration,
			path,
			httpOnly,
			false
		);

		// TODO: isSecure 수정 필요
	}

	public Cookie deleteCookie(TokenType tokenType) {
		String cookieName = tokenType.name();
		String path = "/";
		boolean httpOnly = tokenType == TokenType.REFRESH_TOKEN;

		return CookieUtil.deleteCookie(
			cookieName,
			path,
			httpOnly,
			false
		);

		// TODO: isSecure 수정 필요
	}
}
