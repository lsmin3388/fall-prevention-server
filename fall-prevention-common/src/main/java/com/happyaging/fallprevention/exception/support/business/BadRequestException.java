package com.happyaging.fallprevention.exception.support.business;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class BadRequestException extends ApplicationLogicException {
	private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	public BadRequestException(final String errorMsg) {
		super(errorMsg);
	}
}
