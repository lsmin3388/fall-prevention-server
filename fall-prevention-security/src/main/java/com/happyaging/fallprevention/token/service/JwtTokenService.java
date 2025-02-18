package com.happyaging.fallprevention.token.service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.account.persistence.AccountRepository;
import com.happyaging.fallprevention.exception.UserNotFoundException;
import com.happyaging.fallprevention.properties.JwtProperties;
import com.happyaging.fallprevention.security.details.PrincipalDetails;
import com.happyaging.fallprevention.token.dto.Token;
import com.happyaging.fallprevention.token.dto.TokenType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService {
	private final AccountRepository accountRepository;
	private final JwtProperties jwtProperties;
	private SecretKey signKey;

	@PostConstruct
	public void init() {
		this.signKey = new SecretKeySpec(
			jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8),
			"HmacSHA256"
		);
	}

	public Token generateToken(Authentication authentication, TokenType type) {
		if (type == TokenType.ACCESS_TOKEN) {
			return doGenerateToken(authentication, TokenType.ACCESS_TOKEN);
		} else if (type == TokenType.REFRESH_TOKEN) {
			return doGenerateToken(authentication, TokenType.REFRESH_TOKEN);
		}

		throw new IllegalArgumentException("Unsupported token type: " + type);
	}

	private Token doGenerateToken(Authentication jwtAuthentication, TokenType type) {
		String token = Jwts.builder()
			.header().add(buildHeader(type)).and()
			.claims(buildPayload(jwtAuthentication))
			.expiration(buildExpiration(jwtProperties.getExpiration(type)))
			.signWith(signKey)
			.compact();

		return new Token(jwtProperties.getBearerType(), type, token);
	}

	private Map<String, Object> buildHeader(TokenType type) {
		return Map.of(
			"typ", "JWT",
			"cat", type.name(),
			"alg", "HS256",
			"regDate", System.currentTimeMillis()
		);
	}

	private Map<String, Object> buildPayload(Authentication authentication) {
		return Map.of(
			"email", authentication.getName()
		);
	}

	private Date buildExpiration(Integer expirationSeconds) {
		Date date = new Date(System.currentTimeMillis() + expirationSeconds * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");

		// 초를 일, 시간, 분으로 변환
		int days = expirationSeconds / (24 * 3600);
		int hours = (expirationSeconds % (24 * 3600)) / 3600;
		int minutes = (expirationSeconds % 3600) / 60;
		int seconds = expirationSeconds % 60;

		System.out.println("만료 시간: " + sdf.format(date)); // 날짜 로그
		System.out.println("남은 기간: " + days + "일 " + hours + "시간 " + minutes + "분 " + seconds + "초"); // 변환 로그

		return date;
	}


	public Jws<Claims> extractClaims(String tokenValue) {
		return Jwts.parser()
			.verifyWith(signKey)
			.build()
			.parseSignedClaims(tokenValue);
	}

	public String extractEmail(Jws<Claims> claimsJws) {
		return claimsJws.getPayload().get("email", String.class);
	}

	public String resolveAccessToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(jwtProperties.getAuthHeader());

		if (bearerToken != null && bearerToken.startsWith(jwtProperties.getBearerType())) {
			return bearerToken.replaceAll(jwtProperties.getBearerType(), "").trim();
		}

		return null;
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		// For Web
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (TokenType.REFRESH_TOKEN.name().equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}

		// For App
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith(jwtProperties.getBearerType())) {
			return bearerToken.replaceAll(jwtProperties.getBearerType(), "").trim();
		}

		return null;
	}

	public Authentication getAuthentication(String email) {
		Account account = accountRepository.findByEmail(email)
			.orElseThrow(UserNotFoundException::new);
		PrincipalDetails principalDetails = PrincipalDetails.from(account);

		return new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
	}
}
