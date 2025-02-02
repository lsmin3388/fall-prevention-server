package com.happyaging.fallprevention.survey.entity.question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_option_id")
    private Long id;

    @Column(nullable = false)
    private Integer orderNumber;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Setter
    @ManyToOne
    @JoinColumn(name = "next_question_id")
    private Question nextQuestion;

    @Builder
    public QuestionOption(Integer orderNumber, Question question, String content, Question nextQuestion) {
        this.orderNumber = orderNumber;
        setQuestion(question);
        this.content = content;
        this.nextQuestion = nextQuestion;
    }

    public void update(QuestionOption updateQuestionOption) {
        this.content = updateQuestionOption.getContent();
        this.nextQuestion = updateQuestionOption.getNextQuestion();
    }

    public void setQuestion(Question question) {
        if (this.question != null) {
            this.question.getOptions().remove(this);
        }
        this.question = question;

        if (!question.getOptions().contains(this)) {
            question.getOptions().add(this);
        }
    }

}
