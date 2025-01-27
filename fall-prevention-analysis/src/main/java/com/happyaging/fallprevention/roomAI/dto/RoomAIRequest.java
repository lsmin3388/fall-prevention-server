package com.happyaging.fallprevention.roomAI.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.happyaging.fallprevention.roomAI.entity.RoomAIImage;
import com.happyaging.fallprevention.roomAI.entity.RoomAIPrompt;
import com.happyaging.fallprevention.roomAI.entity.RoomCategory;

public record RoomAIRequest(
	String roomName,
	RoomCategory roomCategory,
	List<String> roomImages
) {

	public static List<RoomAIPrompt> toRoomAiPrompt(List<RoomAIRequest> roomAIRequests) {
		return roomAIRequests.stream()
			.map(RoomAIRequest::toRoomAiPrompt)
			.toList();
	}

	public RoomAIPrompt toRoomAiPrompt() {
		List<RoomAIImage> roomAIImages = roomImages.stream()
			.map(originalFilename -> RoomAIImage.builder().filename(originalFilename).build())
			.collect(Collectors.toList()); // Mutable list

		RoomAIPrompt roomAIPrompt = RoomAIPrompt.builder()
			.roomName(roomName)
			.roomCategory(roomCategory)
			.roomAIImages(roomAIImages)
			.build();

		roomAIImages.forEach(image -> image.connectRoomAIPrompt(roomAIPrompt));

		return roomAIPrompt;
	}
}
