package com.happyaging.fallprevention.senior.exception;

import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;

public class SeniorNotFoundException extends UnauthorizedException {
	private static final String errorMsg = "SENIOR_NOT_FOUND";
	public SeniorNotFoundException() {
		super(errorMsg);
	}
}
