package com.happyaging.fallprevention.roomAI.usecase.dto.response;

import java.util.List;

import com.happyaging.fallprevention.roomAI.entity.RoomAI;
import com.happyaging.fallprevention.roomAI.entity.RoomAIPrompt;

import lombok.Builder;

@Builder
public record RoomAIResponse(
	Long roomAIId,
	List<RoomAIPromptResponse> roomAIPrompts
) {
	public static RoomAIResponse from(RoomAI roomAI, List<RoomAIPrompt> roomAIPrompts) {
		List<RoomAIPromptResponse> roomAIPromptResponses = roomAIPrompts.stream()
			.map(RoomAIPromptResponse::from)
			.toList();

		return RoomAIResponse.builder()
			.roomAIId(roomAI.getId())
			.roomAIPrompts(roomAIPromptResponses)
			.build();
	}

	public static RoomAIResponse from(RoomAI roomAI) {
		List<RoomAIPromptResponse> roomAIPromptResponses = roomAI.getRoomAIPrompt().stream()
			.map(RoomAIPromptResponse::from)
			.toList();

		return RoomAIResponse.builder()
			.roomAIId(roomAI.getId())
			.roomAIPrompts(roomAIPromptResponses)
			.build();
	}

}
