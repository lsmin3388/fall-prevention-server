package com.happyaging.fallprevention.token.service;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.happyaging.fallprevention.exception.RefreshTokenExpiredException;
import com.happyaging.fallprevention.exception.RefreshTokenInvalidException;
import com.happyaging.fallprevention.properties.JwtProperties;
import com.happyaging.fallprevention.security.details.PrincipalDetails;
import com.happyaging.fallprevention.token.dto.JwtTokens;
import com.happyaging.fallprevention.token.dto.Token;
import com.happyaging.fallprevention.token.dto.TokenType;
import com.happyaging.fallprevention.token.entity.RefreshToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
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

		// RefreshToken DB 저장
		String email = ((PrincipalDetails) authentication.getPrincipal()).getUsername();
		refreshTokenService.updateRefreshToken(
			RefreshToken.builder()
				.email(email)
				.refreshToken(refreshToken.value())
				.build()
		);

		return JwtTokens.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public JwtTokens reissueTokens(String refreshTokenValue) {
		try {
			// 토큰 파싱
			Jws<Claims> refreshClaims = jwtTokenService.extractClaims(refreshTokenValue);
			String email = jwtTokenService.extractEmail(refreshClaims);

			// DB에 저장되어 있는 RefreshToken 과 비교
			String storedRefreshToken = refreshTokenService.getRefreshToken(email);
			if (!Objects.equals(storedRefreshToken, refreshTokenValue)) {
				throw new RefreshTokenInvalidException();
			}

			// 인증 객체 생성
			Authentication authentication = jwtTokenService.getAuthentication(email);

			// 새 토큰 생성 (Refresh Token Rotation)
			Token newAccessToken = jwtTokenService.generateToken(authentication, TokenType.ACCESS_TOKEN);
			Token newRefreshToken = jwtTokenService.generateToken(authentication, TokenType.REFRESH_TOKEN);

			// DB 저장 (Refresh Token 갱신)
			refreshTokenService.updateRefreshToken(
				RefreshToken.builder()
					.email(email)
					.refreshToken(newRefreshToken.value())
					.build()
			);

			return JwtTokens.builder()
				.accessToken(newAccessToken)
				.refreshToken(newRefreshToken)
				.build();

		} catch (ExpiredJwtException e) {
			// 만료된 토큰
			throw new RefreshTokenExpiredException();
		} catch (JwtException e) {
			// 잘못된 토큰
			throw new RefreshTokenInvalidException();
		} catch (Exception e) {
			// 기타 에러
			throw e;
		}
	}

	public void logout(String refreshTokenValue) {
		if (refreshTokenValue == null) return;
		try {
			Jws<Claims> claims = jwtTokenService.extractClaims(refreshTokenValue);
			String email = jwtTokenService.extractEmail(claims);

			String stored = refreshTokenService.getRefreshToken(email);
			if (Objects.equals(stored, refreshTokenValue)) {
				refreshTokenService.deleteRefreshToken(email);
			}
		} catch (ExpiredJwtException e) {
			// 만료되었지만 email 파싱 가능하면 DB에서 삭제
			Claims claims = e.getClaims();
			if (claims != null) {
				String email = claims.get("email", String.class);
				if (email != null) {
					refreshTokenService.deleteRefreshToken(email);
				}
			}
		}
	}
}
