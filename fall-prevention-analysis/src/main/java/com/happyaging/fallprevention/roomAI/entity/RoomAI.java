package com.happyaging.fallprevention.roomAI.entity;

import java.util.List;

import com.happyaging.fallprevention.account.entity.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "room_ai")
public class RoomAI {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_ai_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@OneToMany(
		mappedBy = "roomAI",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<RoomAIPrompt> roomAIPrompt;

	@Builder
	public RoomAI(Long id, Account account, List<RoomAIPrompt> roomAIPrompt) {
		this.id = id;
		this.account = account;
		this.roomAIPrompt = roomAIPrompt;
	}

	public void connectRoomAIPrompt(List<RoomAIPrompt> roomAIPrompt) {
		this.roomAIPrompt = roomAIPrompt;
	}
}
