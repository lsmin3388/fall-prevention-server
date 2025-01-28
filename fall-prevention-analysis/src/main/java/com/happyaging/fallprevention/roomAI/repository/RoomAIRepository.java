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
	@Query("SELECT r.createdAt FROM RoomAI r WHERE r.account.id = :accountId")
	List<LocalDateTime> findCreatedAtByAccountId(@Param("accountId") Long accountId);

	@EntityGraph(attributePaths = {"roomAIPrompt", "roomAIPrompt.roomAIImages"})
	Optional<RoomAI> findByAccount_IdAndCreatedAt(Long accountId, LocalDateTime createdAt);
}
