package com.happyaging.fallprevention.senior.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class SeniorNotFoundException extends NotFoundException {
	private static final String errorMsg = "SENIOR_NOT_FOUND";
	public SeniorNotFoundException() {
		super(errorMsg);
	}
}
