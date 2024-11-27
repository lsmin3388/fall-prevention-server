package com.happyaging.fallprevention.exception;

import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;

public class AccessTokenExpiredException extends UnauthorizedException {
	private static final String errorMsg = "ACCESS_TOKEN_EXPIRED";

	public AccessTokenExpiredException() {
		super(errorMsg);
	}
}
