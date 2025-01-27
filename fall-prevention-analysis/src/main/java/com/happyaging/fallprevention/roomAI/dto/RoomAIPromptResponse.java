package com.happyaging.fallprevention.roomAI.dto;

import java.util.List;

import com.happyaging.fallprevention.roomAI.entity.RoomAIImage;
import com.happyaging.fallprevention.roomAI.entity.RoomAIPrompt;
import com.happyaging.fallprevention.roomAI.entity.RoomCategory;

import lombok.Builder;

@Builder
public record RoomAIPromptResponse(
	Long roomAIPromptId, String response,
	RoomCategory roomCategory, List<String> images
) {
	public static RoomAIPromptResponse from(RoomAIPrompt roomAIPrompts) {
		List<RoomAIImage> roomAIImages = roomAIPrompts.getRoomAIImages();
		List<String> images = roomAIImages.stream()
			.map(RoomAIImage::getFilename)
			.toList();

		return RoomAIPromptResponse.builder()
			.roomAIPromptId(roomAIPrompts.getId())
			.response(roomAIPrompts.getResponse())
			.roomCategory(roomAIPrompts.getRoomCategory())
			.images(images)
			.build();
	}
}
