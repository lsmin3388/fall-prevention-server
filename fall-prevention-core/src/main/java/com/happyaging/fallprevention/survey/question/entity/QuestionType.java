package com.happyaging.fallprevention.survey.question.entity;

import lombok.Getter;

@Getter
public enum QuestionType {
    TRUE_FALSE("참거짓"),
    MULTIPLE_CHOICE("객관식"),
    SHORT_ANSWER("주관식");

    private final String type;

    QuestionType(String type) {
        this.type = type;
    }

    public static QuestionType fromString(String type) {
        for (QuestionType q : QuestionType.values()) {
            if (q.type.equalsIgnoreCase(type)) {
                return q;
            }
        }
        return null;
    }
}
