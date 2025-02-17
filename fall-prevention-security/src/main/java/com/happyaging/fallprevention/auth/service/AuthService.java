package com.happyaging.fallprevention.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.account.entity.AccountRole;
import com.happyaging.fallprevention.account.exception.AccountNotFoundException;
import com.happyaging.fallprevention.account.persistence.AccountRepository;
import com.happyaging.fallprevention.auth.exception.EmailDuplicatedException;
import com.happyaging.fallprevention.auth.usecase.AuthUseCase;
import com.happyaging.fallprevention.auth.usecase.dto.LoginRequestDto;
import com.happyaging.fallprevention.auth.usecase.dto.RegisterRequestDto;
import com.happyaging.fallprevention.token.dto.JwtTokens;
import com.happyaging.fallprevention.token.service.AuthenticationService;

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
		if (accountRepository.existsByEmail(requestDto.email())) {
			throw new EmailDuplicatedException();
		}
		accountRepository.save(requestDto.toEntity(passwordEncoder));
	}

	@Override
	@Transactional
	public JwtTokens login(LoginRequestDto requestDto) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
		);

		return authenticationService.issueToken(authentication);
	}

	@Override
	@Transactional
	public JwtTokens adminLogin(LoginRequestDto requestDto) {
		JwtTokens jwtTokens = login(requestDto);
		Account account = accountRepository.findByEmail(requestDto.email())
			.orElseThrow(AccountNotFoundException::new);
		if (account.getRole() != AccountRole.ADMIN) throw new AccountNotFoundException();
		return jwtTokens;
	}

	@Override
	@Transactional
	public JwtTokens refresh(String refreshToken) {
		return authenticationService.reissueTokens(refreshToken);
	}

	@Override
	@Transactional
	public void logout(String refreshToken) {
		authenticationService.logout(refreshToken);
	}
}

