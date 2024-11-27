package com.happyaging.fallprevention.exception;

import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;

public class UserNotFoundException extends UnauthorizedException {
	private static final String errorMsg = "UNAUTHORIZED_USER";

	public UserNotFoundException() {
		super(errorMsg);
	}
}
