package com.happyaging.fallprevention.exception.support.security;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class ForbiddenException extends SecurityException {
	public ForbiddenException(final String errorMsg) {
		super(HttpStatus.FORBIDDEN, errorMsg);
	}
}
