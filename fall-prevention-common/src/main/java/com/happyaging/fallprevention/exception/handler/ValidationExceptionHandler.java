package com.happyaging.fallprevention.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.happyaging.fallprevention.exception.BaseExceptionHandler;
import com.happyaging.fallprevention.exception.ExceptionHandlerOrder;
import com.happyaging.fallprevention.util.api.ApiErrorResult;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(ExceptionHandlerOrder.VALIDATE_EXCEPTION_HANDLER)
public class ValidationExceptionHandler extends BaseExceptionHandler<Exception> {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		log.error("Validation failed", exception);
		return handleException(exception, HttpStatus.BAD_REQUEST, "VALIDATION_FAILED");
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiErrorResult> handleConstraintViolationException(ConstraintViolationException exception) {
		log.error("Validation failed", exception);
		return handleException(exception, HttpStatus.BAD_REQUEST, "VALIDATION_FAILED");
	}
}
