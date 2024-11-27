package com.happyaging.fallprevention.exception.support.security;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class UnauthorizedException extends SecurityException {
	private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
	public UnauthorizedException(final String errorMsg) {
		super(errorMsg);
	}
}
