package com.happyaging.fallprevention.exception.support.security;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class ForbiddenException extends SecurityException{
	private final HttpStatus httpStatus = HttpStatus.FORBIDDEN;
	public ForbiddenException(final String errorMsg) {
		super(errorMsg);
	}
}
