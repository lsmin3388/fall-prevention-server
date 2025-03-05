package com.happyaging.fallprevention.youtube.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class VideoNotFoundException extends NotFoundException {
	private static final String errorMsg = "VIDEO_NOT_FOUND";
	public VideoNotFoundException() {
		super(errorMsg);
	}
}
