package com.happyaging.fallprevention.gpt.dto.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GPTVisionRequest {
	private String model;
	private List<GPTMessage> messages;
	private Integer max_tokens;

	@Builder
	public GPTVisionRequest(String model, String role, String prompt, List<String> base64Images, Integer maxTokens) {
		this.model = model;
		this.max_tokens = maxTokens;

		List<GPTContent> contents = new ArrayList<>();
		contents.add(new GPTContent("text", prompt));
		base64Images.forEach(base64Image ->
			contents.add(new GPTContent("image_url", new GPTImageUrl("data:image/jpeg;base64," + base64Image)))
		);

		this.messages = Collections.singletonList(new GPTMessage(role, contents));
	}
}
