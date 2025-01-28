package com.happyaging.fallprevention.roomAI.usecase;

import java.util.List;

import com.happyaging.fallprevention.account.entity.Account;
import com.happyaging.fallprevention.roomAI.usecase.dto.request.RoomAIRequest;
import com.happyaging.fallprevention.roomAI.usecase.dto.response.RoomAIResponse;

public interface RoomAIUseCase {
	RoomAIResponse analyzeRoom(Account account, List<RoomAIRequest> roomAIRequest);
	List<String> getAnalysisDateList(Account account);
	RoomAIResponse getAnalysisResult(Account account, String date);
}
