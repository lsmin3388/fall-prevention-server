package com.happyaging.fallprevention.auth.usecase;

import com.happyaging.fallprevention.auth.usecase.dto.LoginRequestDto;
import com.happyaging.fallprevention.auth.usecase.dto.RegisterRequestDto;
import com.happyaging.fallprevention.token.dto.JwtTokens;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthUseCase {
	void register(RegisterRequestDto requestDto);
	JwtTokens login(LoginRequestDto requestDto, HttpServletResponse response);
	JwtTokens refresh(HttpServletRequest request, HttpServletResponse response);
}
