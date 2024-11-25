package com.happyaging.fallprevention.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.happyaging.fallprevention.exception.BaseExceptionHandler;
import com.happyaging.fallprevention.exception.ExceptionHandlerOrder;
import com.happyaging.fallprevention.exception.support.business.ApplicationLogicException;
import com.happyaging.fallprevention.exception.support.business.BadRequestException;
import com.happyaging.fallprevention.exception.support.business.DuplicatedException;
import com.happyaging.fallprevention.exception.support.business.NotFoundException;
import com.happyaging.fallprevention.util.api.ApiErrorResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(ExceptionHandlerOrder.BUSINESS_EXCEPTION_HANDLER)
public class BusinessExceptionHandler extends BaseExceptionHandler<Exception> {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiErrorResult> handleBadRequestException(BadRequestException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}

	@ExceptionHandler(DuplicatedException.class)
	public ResponseEntity<ApiErrorResult> handleDuplicatedException(DuplicatedException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiErrorResult> handleNotFoundException(NotFoundException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}

	@ExceptionHandler(ApplicationLogicException.class)
	public ResponseEntity<ApiErrorResult> handleApplicationLogicException(ApplicationLogicException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}
}
