package survey.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class QuestionOptionNotFoundException extends NotFoundException {
    private static final String errorMsg = "QUESTION_OPTION_NOT_FOUND";
    public QuestionOptionNotFoundException() {super(errorMsg);}
}

