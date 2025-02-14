package com.happyaging.fallprevention.survey.question.entity.option;

import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class OptionId implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "question_id")
	private Long questionId;

	@Column(name = "option_number")
	private Integer optionNumber;

	@Builder
	public OptionId(Long questionId, Integer optionNumber) {
		this.questionId = questionId;
		this.optionNumber = optionNumber;
	}
}
