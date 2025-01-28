package com.happyaging.fallprevention.exception.support.security;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class UnauthorizedException extends SecurityException {
	public UnauthorizedException(final String errorMsg) {
		super(HttpStatus.UNAUTHORIZED, errorMsg);
	}
}
