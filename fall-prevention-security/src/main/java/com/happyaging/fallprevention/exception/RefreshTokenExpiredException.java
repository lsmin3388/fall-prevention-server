package com.happyaging.fallprevention.exception;

import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;

public class RefreshTokenExpiredException extends UnauthorizedException {
	private static final String errorMsg = "REFRESH_TOKEN_EXPIRED";

	public RefreshTokenExpiredException() {
		super(errorMsg);
	}
}
