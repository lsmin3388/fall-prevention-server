package com.happyaging.fallprevention.survey.survey.entity;

import java.util.ArrayList;
import java.util.List;

import com.happyaging.fallprevention.survey.question.entity.Question;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "response",
    uniqueConstraints = @UniqueConstraint(columnNames = {"survey_id", "question_id"})
)
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long id;

    // 응답이 속한 설문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    // 해당 응답이 대상하는 질문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // Option (주관식인 경우 null)
    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResponseSelectedOption> options = new ArrayList<>();

    // 응답 값(주관식 텍스트 입력)
    @Column(length = 4000)
    private String answerText;

    @Builder
    public Response(Long id, Survey survey, Question question, List<ResponseSelectedOption> options,
        String answerText) {
        this.id = id;
        this.survey = survey;
        this.question = question;
        this.options = (options != null) ? options : new ArrayList<>();
        this.answerText = answerText;
    }

    public void addSurvey(Survey survey) {
        this.survey = survey;
    }
}
