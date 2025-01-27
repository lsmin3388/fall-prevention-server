package com.happyaging.fallprevention.roomAI.usecase;

import java.util.List;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.roomAI.dto.RoomAIRequest;
import com.happyaging.fallprevention.roomAI.dto.RoomAIResponse;

public interface RoomAIUseCase {
	RoomAIResponse analyzeRoom(Account account, List<RoomAIRequest> roomAIRequest);
}
