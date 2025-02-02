package com.happyaging.fallprevention.survey.entity.reponse;

import com.happyaging.fallprevention.survey.entity.question.QuestionOption;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_option_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "response_id")
    private Response response;

    @ManyToOne
    @JoinColumn(name = "question_option_id")
    private QuestionOption questionOption;

    @Builder
    public ResponseOption(QuestionOption questionOption, Response response) {
        this.questionOption = questionOption;
        this.response = response;
    }
}