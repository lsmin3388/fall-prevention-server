package com.happyaging.fallprevention.survey.question.entity;

import com.happyaging.fallprevention.survey.question.exception.IncorrectEnumException;

import lombok.Getter;

@Getter
public enum QuestionCategory {
    SENIOR_INFO("어르신 정보"),
    DISEASE("질병 관련"),
    BODY("몸 상태"),
    EXERCISE("운동 관련"),
    FALL_EXPERIENCE("낙상 경험"),
    RESIDENT("주거 환경");

    private final String category;

    QuestionCategory(String category) {
        this.category = category;
    }

    public static QuestionCategory fromString(String category) {
        for (QuestionCategory q : QuestionCategory.values()) {
            if (q.category.equalsIgnoreCase(category)) {
                return q;
            }
        }
        throw new IncorrectEnumException();
    }
}
