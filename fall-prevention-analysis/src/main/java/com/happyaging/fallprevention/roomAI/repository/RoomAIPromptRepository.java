package com.happyaging.fallprevention.roomAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyaging.fallprevention.roomAI.entity.RoomAIPrompt;

public interface RoomAIPromptRepository extends JpaRepository<RoomAIPrompt, Long> {
}
