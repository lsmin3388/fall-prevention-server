package com.happyaging.fallprevention.storage.exception;

import com.happyaging.fallprevention.exception.support.business.BadRequestException;

public class FilePathInvalidException extends BadRequestException {
	private static final String errorMsg = "INVALID_FILE_PATH";
	public FilePathInvalidException() {
		super(errorMsg);
	}
}
