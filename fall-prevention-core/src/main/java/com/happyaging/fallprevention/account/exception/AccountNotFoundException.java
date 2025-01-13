package com.happyaging.fallprevention.account.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
	private static final String errorMsg = "ACCOUNT_NOT_FOUND";
	public AccountNotFoundException() {
		super(errorMsg);
	}
}
