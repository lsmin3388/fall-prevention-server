package com.happyaging.fallprevention.survey.question.entity.questionToProduct;

import com.happyaging.fallprevention.product.entity.Product;
import com.happyaging.fallprevention.survey.question.entity.Question;

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
public class QuestionProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_question_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	@Column(nullable = false)
	private Integer priority;

	@Builder
	public QuestionProduct(Long id, Product product, Question question, Integer priority) {
		this.id = id;
		this.product = product;
		this.question = question;
		this.priority = priority;
	}
}
