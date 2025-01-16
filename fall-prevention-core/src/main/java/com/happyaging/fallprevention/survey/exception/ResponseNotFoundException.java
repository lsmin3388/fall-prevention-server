package com.happyaging.fallprevention.survey.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class ResponseNotFoundException extends NotFoundException {
    private static final String errorMsg = "RESPONSE_NOT_FOUND";
    public ResponseNotFoundException() {super(errorMsg);}
}
