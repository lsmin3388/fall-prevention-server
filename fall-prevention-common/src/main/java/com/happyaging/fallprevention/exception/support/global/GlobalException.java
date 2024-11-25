package com.happyaging.fallprevention.exception.support.global;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
	private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	private final String errorMsg;

	public GlobalException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
}
