package com.happyaging.fallprevention.auth.usecase;

import com.happyaging.fallprevention.auth.usecase.dto.LoginRequestDto;
import com.happyaging.fallprevention.auth.usecase.dto.RegisterRequestDto;
import com.happyaging.fallprevention.token.dto.JwtTokens;

public interface AuthUseCase {
	void register(RegisterRequestDto requestDto);
	JwtTokens login(LoginRequestDto requestDto);
	JwtTokens refresh(String refreshToken);
	void logout(String refreshToken);
}
