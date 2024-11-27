package com.happyaging.fallprevention.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.happyaging.fallprevention.exception.BaseExceptionHandler;
import com.happyaging.fallprevention.exception.ExceptionHandlerOrder;
import com.happyaging.fallprevention.util.api.ApiErrorResult;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(ExceptionHandlerOrder.EXTERNAL_EXCEPTION_HANDLER)
public class ExternalExceptionHandler extends BaseExceptionHandler<Exception> {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResult> handleException(Exception exception) {
		log.error("Unhandled exception occurred", exception);
		return handleException(exception, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ApiErrorResult> handleValidationException(ValidationException exception) {
		return handleException(exception, HttpStatus.BAD_REQUEST, exception.getMessage());
	}
}
