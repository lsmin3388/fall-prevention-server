package com.happyaging.fallprevention.token.service;

import java.lang.reflect.Member;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.happyaging.fallprevention.account.domain.Account;
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

@Component
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
		return new Date(System.currentTimeMillis() + expirationSeconds * 1000);
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
			return bearerToken.replace(jwtProperties.getBearerType(), "").trim();
		}

		return null;
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(TokenType.REFRESH_TOKEN.name())) {
					return cookie.getValue();
				}
			}
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
