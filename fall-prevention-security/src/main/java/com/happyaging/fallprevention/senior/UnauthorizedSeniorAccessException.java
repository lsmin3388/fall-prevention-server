package com.happyaging.fallprevention.senior;

import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;

public class UnauthorizedSeniorAccessException extends UnauthorizedException {
	private static final String errorMsg = "UNAUTHORIZED_SENIOR_ACCESS";

	public UnauthorizedSeniorAccessException() {
		super(errorMsg);
	}
}
