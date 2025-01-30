package com.happyaging.fallprevention.exception;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.happyaging.fallprevention.util.api.ApiErrorResult;
import com.happyaging.fallprevention.util.api.ApiResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class BaseExceptionHandler<T extends Throwable> {
	@Autowired
	private Environment env;

	protected ResponseEntity<ApiErrorResult> handleException(
		T exception,
		HttpStatus status,
		String errorMsg
	) {
		boolean isProdProfile = isProdProfile();

		if (status.is4xxClientError()) {
			if (isProdProfile) {
				log.info("Client error: {}", exception.getMessage());
			} else {
				log.info("Client error: {}", exception.getMessage(), exception);
			}
		}
		else if (status.is5xxServerError()) {
			if (isProdProfile) {
				log.error("Server error: {}", exception.getMessage());
			} else {
				log.error("Server error: {}", exception.getMessage(), exception);
			}
		}
		else {
			if (isProdProfile) {
				log.warn("Unexpected status {}: {}", status, exception.getMessage());
			} else {
				log.warn("Unexpected status {}: {}", status, exception.getMessage(), exception);
			}
		}

		return ResponseEntity
			.status(status)
			.body(ApiResponse.error(status, errorMsg));
	}

	private boolean isProdProfile() {
		return Arrays.asList(env.getActiveProfiles()).contains("prod");
	}
}
