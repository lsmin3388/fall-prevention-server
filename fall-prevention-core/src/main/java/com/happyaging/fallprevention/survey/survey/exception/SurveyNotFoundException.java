package com.happyaging.fallprevention.survey.survey.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class SurveyNotFoundException extends NotFoundException {
	private static final String errorMsg = "SURVEY_NOT_FOUND";
	public SurveyNotFoundException() {
		super(errorMsg);
	}
}
