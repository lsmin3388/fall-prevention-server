package com.happyaging.fallprevention.survey.question.entity;

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

    // 질문 순서
    @Column(unique = true)
    private Integer orderNumber;

    // 질문 내용
    @Column(nullable = false)
    private String content;

    // 질문 관련 이미지 URL
    private String imageUrl;

    // 질문 카테고리
    @Enumerated(EnumType.STRING)
    private QuestionCategory questionCategory;

    // 질문 타입
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    // 객관식일 때 선택지
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    @Builder
    public Question(Long id, Integer orderNumber, String content, String imageUrl,
        QuestionCategory questionCategory, QuestionType questionType, List<Option> options) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.content = content;
        this.imageUrl = imageUrl;
        this.questionCategory = questionCategory;
        this.questionType = questionType;
        this.options = options;
    }

    public void update(QuestionSaveDto questionSaveDto) {
        if (questionSaveDto.orderNumber() != null) {
            this.orderNumber = questionSaveDto.orderNumber();
        }

        if (questionSaveDto.content() != null) {
            this.content = questionSaveDto.content();
        }

        if (questionSaveDto.imageUrl() != null) {
            this.imageUrl = questionSaveDto.imageUrl();
        }

        if (questionSaveDto.category() != null) {
            this.questionCategory = QuestionCategory.fromString(questionSaveDto.category());
        }

        if (questionSaveDto.type() != null) {
            this.questionType = QuestionType.fromString(questionSaveDto.type());
        }
    }
}
