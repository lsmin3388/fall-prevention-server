package com.happyaging.fallprevention.exception;

import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;

public class AccessTokenInvalidException extends UnauthorizedException {
	private static final String errorMsg = "ACCESS_TOKEN_INVALID";

	public AccessTokenInvalidException() {
		super(errorMsg);
	}
}
