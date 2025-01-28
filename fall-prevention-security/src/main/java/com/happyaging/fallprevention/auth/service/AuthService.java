package com.happyaging.fallprevention.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.account.persistence.AccountRepository;
import com.happyaging.fallprevention.auth.exception.EmailDuplicatedException;
import com.happyaging.fallprevention.auth.usecase.AuthUseCase;
import com.happyaging.fallprevention.auth.usecase.dto.LoginRequestDto;
import com.happyaging.fallprevention.auth.usecase.dto.RegisterRequestDto;
import com.happyaging.fallprevention.token.dto.JwtTokens;
import com.happyaging.fallprevention.token.service.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService implements AuthUseCase {
	private final AccountRepository accountRepository;

	private final AuthenticationManager authenticationManager;
	private final AuthenticationService authenticationService;
	private final PasswordEncoder passwordEncoder;


	@Override
	@Transactional
	public void register(RegisterRequestDto requestDto) {
		if (accountRepository.existsByEmail(requestDto.email()))
			throw new EmailDuplicatedException();

		accountRepository.save(requestDto.toEntity(passwordEncoder));
	}

	@Override
	@Transactional
	public JwtTokens login(LoginRequestDto requestDto, HttpServletResponse response) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
		);

		JwtTokens jwtTokens = authenticationService.issueToken(authentication);

		Cookie accessTokenCookie = authenticationService.createCookie(jwtTokens.accessToken());
		Cookie refreshTokenCookie = authenticationService.createCookie(jwtTokens.refreshToken());

		response.addCookie(accessTokenCookie);
		response.addCookie(refreshTokenCookie);

		return jwtTokens;
	}

	@Override
	@Transactional
	public JwtTokens refresh(HttpServletRequest request, HttpServletResponse response) {
		// 1) Refresh Token (쿠키 or 헤더) 추출
		String refreshTokenValue = authenticationService.resolveRefreshToken(request);

		// 2) 새 Access / Refresh 발급
		JwtTokens newTokens = authenticationService.reissueTokens(refreshTokenValue);

		// 3) 쿠키에  세팅 (Refresh Token 은 httpOnly)
		Cookie accessTokenCookie = authenticationService.createCookie(newTokens.accessToken());
		Cookie refreshTokenCookie = authenticationService.createCookie(newTokens.refreshToken());

		response.addCookie(accessTokenCookie);
		response.addCookie(refreshTokenCookie);

		return newTokens;
	}
}
