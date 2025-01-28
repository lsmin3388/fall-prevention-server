package com.happyaging.fallprevention.auth.exception;

import com.happyaging.fallprevention.exception.support.business.DuplicatedException;

public class EmailDuplicatedException extends DuplicatedException {
	private static final String errorMsg = "EMAIL_DUPLICATED";
	public EmailDuplicatedException() {
		super(errorMsg);
	}
}
