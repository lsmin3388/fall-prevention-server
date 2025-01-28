package com.happyaging.fallprevention.exception.support.security;

import org.springframework.http.HttpStatus;

import com.happyaging.fallprevention.exception.support.global.GlobalException;

import lombok.Getter;

@Getter
public abstract class SecurityException extends GlobalException {
	private final HttpStatus httpStatus;

	public SecurityException(HttpStatus httpStatus, String errorMsg) {
		super(errorMsg);
		this.httpStatus = httpStatus;
	}
}
