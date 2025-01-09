package com.happyaging.fallprevention.storage.exception;

import com.happyaging.fallprevention.exception.support.business.NotFoundException;

public class FileNotFoundException extends NotFoundException {
	private static final String errorMsg = "FILE_NOT_FOUND";
	public FileNotFoundException() {
		super(errorMsg);
	}
}
