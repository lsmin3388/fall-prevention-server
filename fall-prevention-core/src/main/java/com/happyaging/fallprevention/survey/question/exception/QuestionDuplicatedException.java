package com.happyaging.fallprevention.survey.question.exception;

import com.happyaging.fallprevention.exception.support.business.DuplicatedException;

public class QuestionDuplicatedException extends DuplicatedException {
	private static final String errorMsg = "QUESTION_DUPLICATED";
	public QuestionDuplicatedException() {
		super(errorMsg);
	}
}
