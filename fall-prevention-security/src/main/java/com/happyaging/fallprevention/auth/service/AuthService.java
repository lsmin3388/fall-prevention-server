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
}
