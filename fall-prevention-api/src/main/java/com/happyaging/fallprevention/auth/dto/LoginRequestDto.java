package com.happyaging.fallprevention.auth.dto;

public record LoginRequestDto(
	String email,
	String password
) {
}
