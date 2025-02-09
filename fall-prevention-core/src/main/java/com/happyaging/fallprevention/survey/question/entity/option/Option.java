package com.happyaging.fallprevention.survey.question.entity.option;

import com.happyaging.fallprevention.survey.question.entity.Question;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {
    @EmbeddedId
    private OptionId id;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    // 실제 보기에 표시되는 내용
    @Column(nullable = false)
    private String content;

    // 이동할 다음 질문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_question_id")
    private Question nextQuestion;

    @Builder
    public Option(OptionId id, Question question, String content, Question nextQuestion) {
        this.id = id;
        this.question = question;
        this.content = content;
        this.nextQuestion = nextQuestion;
    }
}
