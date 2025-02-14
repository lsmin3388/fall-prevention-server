package com.happyaging.fallprevention.survey.question.exception;

import com.happyaging.fallprevention.exception.support.business.BadRequestException;

public class IncorrectEnumException extends BadRequestException {
	private static final String errorMsg = "INCORRECT_ENUM_TYPE";
	public IncorrectEnumException() {
		super(errorMsg);
	}
}
