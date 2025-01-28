package com.happyaging.fallprevention.roomAI.usecase;

import java.util.List;

import com.happyaging.fallprevention.roomAI.usecase.dto.request.RoomAIRequest;
import com.happyaging.fallprevention.roomAI.usecase.dto.response.RoomAIResponse;

public interface RoomAIUseCase {
	RoomAIResponse analyzeRoom(Long seniorId, List<RoomAIRequest> roomAIRequest);
	List<String> getAnalysisDateList(Long seniorId);
	RoomAIResponse getAnalysisResult(Long seniorId, String date);
}
