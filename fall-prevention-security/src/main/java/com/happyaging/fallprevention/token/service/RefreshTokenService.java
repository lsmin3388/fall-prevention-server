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

	@Transactional(readOnly = true)
	@Cacheable(value = "refreshTokens", key = "#p0", unless = "#result == null")
	public String getRefreshToken(String email) {
		return refreshTokenRepository.findByEmail(email)
			.map(RefreshToken::getRefreshToken)
			.orElse(null);
	}

	@Transactional
	@CachePut(value = "refreshTokens", key = "#p0.email")
	public String updateRefreshToken(RefreshToken refreshToken) {
		refreshTokenRepository.save(refreshToken);
		return refreshToken.getRefreshToken();
	}

	@Transactional
	@CacheEvict(value = "refreshTokens", key = "#p0")
	public void deleteRefreshToken(String email) {
		refreshTokenRepository.deleteById(email);
	}
}

