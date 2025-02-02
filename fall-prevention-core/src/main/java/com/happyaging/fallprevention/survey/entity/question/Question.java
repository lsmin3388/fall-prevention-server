package com.happyaging.fallprevention.survey.entity.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.SQLDelete;

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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE question SET is_deleted = 1 WHERE question_id=?")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Setter
    @Column(unique = true)
    private Integer orderNumber;

    @Column(nullable = false)
    private String content;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Setter
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOption> options;

    @Column(nullable = false)
    private final Boolean isDeleted = false;

    @Builder
    public Question(Integer orderNumber, String content, String imageUrl, QuestionCategory category, QuestionType type, List<QuestionOption> options) {
        this.orderNumber = orderNumber;
        this.content = content;
        this.imageUrl = imageUrl;
        this.category = category;
        this.type = type;
        this.options = Objects.requireNonNullElseGet(options, ArrayList::new);
    }

    public void update(Question questionToUpdate) {
        if (questionToUpdate.getContent() != null) {
            this.content = questionToUpdate.getContent();
        }
        if (questionToUpdate.getImageUrl() != null) {
            this.imageUrl = questionToUpdate.getImageUrl();
        }
        if (questionToUpdate.getCategory() != null) {
            this.category = questionToUpdate.getCategory();
        }
        if (questionToUpdate.getType() != null) {
            this.type = questionToUpdate.getType();
        }
        if (questionToUpdate.getOptions() != null) {
            updateOptions(questionToUpdate.getOptions());
        }
    }

    private void updateOptions(List<QuestionOption> updatedOptions) {
        List<QuestionOption> existingOptions = this.options;

        for (int i = 0; i < Math.min(existingOptions.size(), updatedOptions.size()); i++) {
            QuestionOption existingOption = existingOptions.get(i);
            QuestionOption updatedOption = updatedOptions.get(i);
            existingOption.update(updatedOption);
        }

        if (existingOptions.size() > updatedOptions.size()) {
            for (int i = updatedOptions.size(); i < existingOptions.size(); i++) {
                existingOptions.remove(existingOptions.get(i));
            }
        } else if (updatedOptions.size() > existingOptions.size()) {
            for (int i = existingOptions.size(); i < updatedOptions.size(); i++) {
                QuestionOption.builder()
                        .orderNumber(updatedOptions.get(i).getOrderNumber())
                        .question(this)
                        .content(updatedOptions.get(i).getContent())
                        .nextQuestion(updatedOptions.get(i).getNextQuestion())
                        .build();
            }
        }
    }


}
