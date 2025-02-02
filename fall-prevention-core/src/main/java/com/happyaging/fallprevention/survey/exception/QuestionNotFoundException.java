package com.happyaging.fallprevention.survey.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class QuestionNotFoundException extends NotFoundException {
    private static final String errorMsg = "QUESTION_NOT_FOUND";
    public QuestionNotFoundException() {super(errorMsg);}
}
