package com.happyaging.fallprevention.roomAI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room_ai_image")
public class RoomAIImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_ai_image_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_ai_prompt_id")
	private RoomAIPrompt roomAIPrompt;

	@Column(nullable = false)
	private String filename;

	@Builder
	public RoomAIImage(Long id, RoomAIPrompt roomAIPrompt, String filename) {
		this.id = id;
		this.roomAIPrompt = roomAIPrompt;
		this.filename = filename;
	}

	public void connectRoomAIPrompt(RoomAIPrompt roomAIPrompt) {
		this.roomAIPrompt = roomAIPrompt;
	}
}
