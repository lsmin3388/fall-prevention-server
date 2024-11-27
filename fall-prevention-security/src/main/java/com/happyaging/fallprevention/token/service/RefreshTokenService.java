package com.happyaging.fallprevention.token.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.token.entity.RefreshToken;
import com.happyaging.fallprevention.token.persistence.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	/**
	 * Refresh Token 을 Spring Cache 에서 조회한다.
	 * 만약 없다면 DB 에서 조회한 후 Spring Cache 에 저장한다.
	 * @param email 사용자 이메일
	 * @return Refresh Token
	 */
	@Transactional(readOnly = true)
	@Cacheable(value = "refreshTokens", key = "#email", unless = "#result == null")
	public String getRefreshToken(String email) {
		return refreshTokenRepository.findByEmail(email)
			.map(RefreshToken::getRefreshToken)
			.orElse(null);
	}

	/**
	 * Refresh Token 을 Spring Cache 에 저장한다.
	 * @param refreshToken Refresh Token
	 * @return Refresh Token
	 */
	@Transactional
	@CachePut(value = "refreshTokens", key = "#refreshToken.email")
	public String updateRefreshToken(RefreshToken refreshToken) {
		refreshTokenRepository.save(refreshToken);
		return refreshToken.getRefreshToken();
	}

	/**
	 * Refresh Token 을 Spring Cache 에서 삭제한다.
	 * @param email 사용자 이메일
	 */
	@Transactional
	@CacheEvict(value = "refreshTokens", key = "#email")
	public void deleteRefreshToken(String email) {
		refreshTokenRepository.deleteById(email);
	}
}

