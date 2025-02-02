package com.happyaging.fallprevention.storage.exception;

import com.happyaging.fallprevention.exception.support.business.BadRequestException;

public class FileStoreFailedException extends BadRequestException {
	private static final String errorMsg = "FAILED_FILE_STORE";
	public FileStoreFailedException() {
		super(errorMsg);
	}
}
