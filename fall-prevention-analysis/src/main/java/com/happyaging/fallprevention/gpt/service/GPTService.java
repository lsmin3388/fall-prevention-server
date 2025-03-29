package com.happyaging.fallprevention.gpt.service;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyaging.fallprevention.gpt.config.GPTProperties;
import com.happyaging.fallprevention.gpt.dto.request.GPTVisionRequest;
import com.happyaging.fallprevention.storage.service.StorageService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GPTService {
	private WebClient webClient;
	private final StorageService storageService;
	private final GPTProperties gptProperties;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void init() {
		this.webClient = WebClient.builder()
			.baseUrl(gptProperties.getApiUrl())
			.defaultHeaders(httpHeaders -> {
				httpHeaders.setBearerAuth(gptProperties.getApiKey());
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			})
			.build();
	}

	public String callGPTVisionApi(String prompt, List<String> imagesFilename) {
		List<String> base64Images = imagesFilename.stream()
			.map(storageService::loadAsBase64)
			.toList();

		String gptFullResponse = this.webClient.post()
			.bodyValue(
				GPTVisionRequest.builder()
					.model("gpt-4o-mini")
					.role("user")
					.prompt(prompt)
					.base64Images(base64Images)
					.maxTokens(5000)
					.build()
			)
			.retrieve()
			.bodyToMono(String.class)
			.onErrorResume(e -> {
				log.error("callGPTApi failed: {}", e.getMessage());
				return Mono.just("{}");
			})
			.block();

		try {
			JsonNode root = objectMapper.readTree(gptFullResponse);
			JsonNode choices = root.path("choices");
			if (!choices.isMissingNode() && !choices.isEmpty()) {
				JsonNode contentNode = choices.get(0).path("message").path("content");
				if (!contentNode.isMissingNode()) {
					return contentNode.asText();
				}
			}
		} catch (Exception e) {
			log.error("Failed to parse GPT response", e);
		}
		return "";
	}
}
