package com.happyaging.fallprevention.exception.support.business;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class DuplicatedException extends ApplicationLogicException {
	private final HttpStatus httpStatus = HttpStatus.CONFLICT;
	public DuplicatedException(final String errorMsg) {
		super(errorMsg);
	}
}
