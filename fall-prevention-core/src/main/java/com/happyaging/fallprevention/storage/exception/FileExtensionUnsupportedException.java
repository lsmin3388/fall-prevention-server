package com.happyaging.fallprevention.storage.exception;

import com.happyaging.fallprevention.exception.support.business.BadRequestException;

public class FileExtensionUnsupportedException extends BadRequestException {
	private static final String errorMsg = "UNSUPPORTED_FILE_EXTENSION";
	public FileExtensionUnsupportedException() {
		super(errorMsg);
	}
}
