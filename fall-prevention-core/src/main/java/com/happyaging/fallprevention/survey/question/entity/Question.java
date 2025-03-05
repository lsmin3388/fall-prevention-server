package com.happyaging.fallprevention.survey.question.entity;

import java.util.ArrayList;
import java.util.List;

import com.happyaging.fallprevention.survey.question.entity.option.Option;
import com.happyaging.fallprevention.survey.question.usecase.dto.request.QuestionSaveDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer questionNumber;

    // 질문 내용
    @Column(nullable = false)
    private String content;

    // 질문 관련 이미지 URL
    private String imageUrl;

    // 질문 가중치
    private Double weight;

    // 질문 카테고리
    @Enumerated(EnumType.STRING)
    private QuestionCategory questionCategory;

    // 질문 타입
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    // 객관식일 때 선택지
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id.optionNumber ASC")
    private List<Option> options = new ArrayList<>();

    @Builder
    public Question(Long id, Integer questionNumber, String content, String imageUrl, Double weight,
        QuestionCategory questionCategory, QuestionType questionType, List<Option> options) {
        this.id = id;
        this.questionNumber = questionNumber;
        this.content = content;
        this.imageUrl = imageUrl;
        this.weight = weight;
        this.questionCategory = questionCategory;
        this.questionType = questionType;
        this.options = options;
    }

    public void update(QuestionSaveDto questionSaveDto) {
        if (questionSaveDto.questionNumber() != null) {
            this.questionNumber = questionSaveDto.questionNumber();
        }

        if (questionSaveDto.content() != null) {
            this.content = questionSaveDto.content();
        }

        if (questionSaveDto.imageUrl() != null) {
            this.imageUrl = questionSaveDto.imageUrl();
        }

        if (questionSaveDto.weight() != null) {
            this.weight = questionSaveDto.weight();
        }

        if (questionSaveDto.category() != null) {
            this.questionCategory = questionSaveDto.category();
        }

        if (questionSaveDto.type() != null) {
            this.questionType = questionSaveDto.type();
        }
    }
}
