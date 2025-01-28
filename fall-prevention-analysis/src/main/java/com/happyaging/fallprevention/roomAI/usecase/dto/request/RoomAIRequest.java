package com.happyaging.fallprevention.roomAI.usecase.dto.request;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.happyaging.fallprevention.roomAI.entity.RoomAIImage;
import com.happyaging.fallprevention.roomAI.entity.RoomAIPrompt;
import com.happyaging.fallprevention.roomAI.entity.RoomCategory;

public record RoomAIRequest(
	String roomName,
	RoomCategory roomCategory,
	List<String> roomImages
) {

	public static Set<RoomAIPrompt> toRoomAiPrompt(List<RoomAIRequest> roomAIRequests) {
		return roomAIRequests.stream()
			.map(RoomAIRequest::toRoomAiPrompt)
			.collect(Collectors.toSet());
	}

	public RoomAIPrompt toRoomAiPrompt() {
		// List<String> → Set<RoomAIImage> 변환
		Set<RoomAIImage> roomAIImages = roomImages.stream()
			.map(filename -> RoomAIImage.builder().filename(filename).build())
			.collect(Collectors.toSet());

		RoomAIPrompt prompt = RoomAIPrompt.builder()
			.roomName(roomName)
			.roomCategory(roomCategory)
			.roomAIImages(roomAIImages)
			.build();

		// 양방향 연관관계 세팅
		roomAIImages.forEach(img -> img.connectRoomAIPrompt(prompt));

		return prompt;
	}
}

