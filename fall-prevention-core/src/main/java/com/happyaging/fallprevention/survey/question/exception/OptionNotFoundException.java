package com.happyaging.fallprevention.survey.question.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class OptionNotFoundException extends NotFoundException {
	private static final String errorMsg = "OPTION_NOT_FOUND";
	public OptionNotFoundException() {
		super(errorMsg);
	}
}
