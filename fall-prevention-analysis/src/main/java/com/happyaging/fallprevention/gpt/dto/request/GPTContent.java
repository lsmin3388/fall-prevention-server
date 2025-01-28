package com.happyaging.fallprevention.gpt.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
class GPTContent {
	private final String type;
	private String text;
	private GPTImageUrl image_url;

	public GPTContent(String type, String text) {
		this.type = type;
		this.text = text;
	}

	public GPTContent(String type, GPTImageUrl image_url) {
		this.type = type;
		this.image_url = image_url;
	}
}
