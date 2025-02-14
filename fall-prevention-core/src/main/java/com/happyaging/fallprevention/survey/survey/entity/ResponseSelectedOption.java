package com.happyaging.fallprevention.survey.survey.entity;

import com.happyaging.fallprevention.survey.question.entity.option.Option;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseSelectedOption {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "response_option_id")
	private Long id;

	// 해당 ResponseOption이 속한 Response
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "response_id", nullable = false)
	private Response response;

	// 선택된 Option (객관식 옵션)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "question_id", referencedColumnName = "question_id"),
		@JoinColumn(name = "option_number", referencedColumnName = "option_number")
	})
	private Option option;

	@Builder
	public ResponseSelectedOption(Response response, Option option) {
		this.response = response;
		this.option = option;
	}
}
