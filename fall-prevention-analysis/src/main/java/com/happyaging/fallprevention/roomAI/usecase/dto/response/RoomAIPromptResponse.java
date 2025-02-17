package com.happyaging.fallprevention.roomAI.usecase.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyaging.fallprevention.roomAI.entity.RoomAIImage;
import com.happyaging.fallprevention.roomAI.entity.RoomAIPrompt;
import com.happyaging.fallprevention.roomAI.entity.RoomCategory;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record RoomAIPromptResponse(
	Long roomAIPromptId,
	RoomAnalysisResponseDTO responseDto,
	RoomCategory roomCategory,
	String roomName,
	List<String> images
) {

	public static RoomAIPromptResponse from(RoomAIPrompt roomAIPrompt) {
		List<String> images = roomAIPrompt.getRoomAIImages().stream()
			.map(RoomAIImage::getFilename)
			.toList();

		String rawResponseJson = roomAIPrompt.getResponse()
			.replaceAll("```", "")
			.replaceAll("json", "");

		RoomAnalysisResponseDTO parsedDto = null;
		if (!rawResponseJson.isBlank()) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				parsedDto = objectMapper.readValue(rawResponseJson, RoomAnalysisResponseDTO.class);
			} catch (Exception e) {
				log.error("Fail to parse GPT response JSON: {}", rawResponseJson, e);
			}
		}

		return RoomAIPromptResponse.builder()
			.roomAIPromptId(roomAIPrompt.getId())
			.responseDto(parsedDto)
			.roomCategory(roomAIPrompt.getRoomCategory())
			.roomName(roomAIPrompt.getRoomName())
			.images(images)
			.build();
	}
}
