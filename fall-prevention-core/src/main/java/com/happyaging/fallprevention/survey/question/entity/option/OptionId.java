package com.happyaging.fallprevention.survey.question.entity.option;

import java.io.Serial;
import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class OptionId implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private Long questionId;
	private Integer optionNumber;

	@Builder
	public OptionId(Long questionId, Integer optionNumber) {
		this.questionId = questionId;
		this.optionNumber = optionNumber;
	}
}
