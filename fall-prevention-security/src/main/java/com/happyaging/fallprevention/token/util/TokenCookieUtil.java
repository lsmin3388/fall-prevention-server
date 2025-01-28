package com.happyaging.fallprevention.token.util;

import org.springframework.stereotype.Component;

import com.happyaging.fallprevention.properties.JwtProperties;
import com.happyaging.fallprevention.token.dto.TokenType;
import com.happyaging.fallprevention.util.cookie.CookieUtil;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCookieUtil {
	private final JwtProperties jwtProperties;

	public Cookie createCookieForAccessToken(String accessToken) {
		return CookieUtil.createCookie(
			TokenType.ACCESS_TOKEN.name(),
			accessToken,
			jwtProperties.getExpiration(TokenType.ACCESS_TOKEN),
			"/",
			false,
			false
		);
	}

	public Cookie createCookieForRefreshToken(String refreshToken) {
		return CookieUtil.createCookie(
			TokenType.REFRESH_TOKEN.name(),
			refreshToken,
			jwtProperties.getExpiration(TokenType.REFRESH_TOKEN),
			"/",
			true,
			false
		);
	}

	public Cookie deleteCookieForAccessToken() {
		return CookieUtil.deleteCookie(TokenType.ACCESS_TOKEN.name(), "/", false, false);
	}

	public Cookie deleteCookieForRefreshToken() {
		return CookieUtil.deleteCookie(TokenType.REFRESH_TOKEN.name(), "/", true, false);
	}
}
