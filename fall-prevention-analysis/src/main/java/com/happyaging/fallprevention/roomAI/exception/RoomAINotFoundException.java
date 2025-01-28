package com.happyaging.fallprevention.roomAI.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class RoomAINotFoundException extends NotFoundException {
	private static final String errorMsg = "ROOM_AI_NOT_FOUND";
	public RoomAINotFoundException() {
		super(errorMsg);
	}
}
