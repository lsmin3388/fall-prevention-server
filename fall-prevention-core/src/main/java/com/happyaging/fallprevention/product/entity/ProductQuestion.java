package com.happyaging.fallprevention.product.entity;

import com.happyaging.fallprevention.survey.entity.question.Question;

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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_question_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product Product;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    //@Setter
    @Column(nullable = false)
    private Integer priority;

    @Builder
    public ProductQuestion(Product product, Question question, Integer priority) {
        this.Product = product;
        this.question = question;
        this.priority = priority;
    }
}
