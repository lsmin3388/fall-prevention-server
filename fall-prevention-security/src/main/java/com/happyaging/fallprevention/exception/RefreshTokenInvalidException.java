package com.happyaging.fallprevention.exception;

import com.happyaging.fallprevention.exception.support.security.ForbiddenException;

public class RefreshTokenInvalidException extends ForbiddenException {
	private static final String errorMsg = "REFRESH_TOKEN_INVALID";

	public RefreshTokenInvalidException() {
		super(errorMsg);
	}
}
