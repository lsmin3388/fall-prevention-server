package com.happyaging.fallprevention.survey.analysis.exception;

import com.happyaging.fallprevention.exception.support.business.BadRequestException;

public class FailedAnalysisException extends BadRequestException {
	private static final String errorMsg = "FAILED_ANALYSIS";
	public FailedAnalysisException() {
		super(errorMsg);
	}
}
