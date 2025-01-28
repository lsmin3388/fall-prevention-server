package com.happyaging.fallprevention.roomAI.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.happyaging.fallprevention.roomAI.entity.RoomAI;

public interface RoomAIRepository extends JpaRepository<RoomAI, Long> {
	@Query("SELECT DISTINCT r.createdAt FROM RoomAI r WHERE r.senior.id = :seniorId ORDER BY r.createdAt DESC")
	List<LocalDateTime> findCreatedAtBySeniorId(@Param("seniorId") Long seniorId);

	@EntityGraph(attributePaths = {"roomAIPrompt", "roomAIPrompt.roomAIImages"})
	Optional<RoomAI> findBySenior_IdAndCreatedAt(Long seniorId, LocalDateTime createdAt);
}
