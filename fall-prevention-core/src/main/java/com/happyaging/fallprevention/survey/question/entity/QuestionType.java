package com.happyaging.fallprevention.survey.question.entity;

import lombok.Getter;

@Getter
public enum QuestionType {
    TRUE_FALSE, // 참거짓
    MULTIPLE_CHOICE, // 객관식
    SHORT_ANSWER // 주관식
}
