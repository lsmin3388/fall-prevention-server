package com.happyaging.fallprevention.gpt.dto.request;

import java.util.List;

record GPTMessage(
	String role,
	List<GPTContent> content
) {
}
