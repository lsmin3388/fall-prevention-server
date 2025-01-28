package com.happyaging.fallprevention.roomAI.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room_ai_prompt")
public class RoomAIPrompt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_ai_prompt_id")
	private Long id;

	@Column(nullable = false)
	private String roomName;

	@Column(nullable = false)
	private RoomCategory roomCategory;

	@Lob
	@Column(name = "response")
	private String response;

	/**
	 * 기존: private List<RoomAIImage> roomAIImages;
	 * 변경: private Set<RoomAIImage> roomAIImages;
	 */
	@OneToMany(
		mappedBy = "roomAIPrompt",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private Set<RoomAIImage> roomAIImages = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "room_ai_id")
	private RoomAI roomAI;

	@Builder
	public RoomAIPrompt(
		Long id, String roomName, RoomCategory roomCategory,
		String response, Set<RoomAIImage> roomAIImages, RoomAI roomAI
	) {
		this.id = id;
		this.roomName = roomName;
		this.roomCategory = roomCategory;
		this.response = response;
		if (roomAIImages != null) this.roomAIImages = roomAIImages;
		this.roomAI = roomAI;
	}


	public void updateResponse(String response) {
		this.response = response;
	}

	public List<String> getImagesFilename() {
		return roomAIImages.stream()
			.map(RoomAIImage::getFilename)
			.collect(Collectors.toList());
	}

	public void connectRoomAI(RoomAI roomAI) {
		this.roomAI = roomAI;
	}
}
