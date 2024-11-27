package com.happyaging.fallprevention.account.exception;

import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;

public class AccountNotFoundException extends UnauthorizedException {
	private static final String errorMsg = "ACCOUNT_NOT_FOUND";
	public AccountNotFoundException() {
		super(errorMsg);
	}
}
