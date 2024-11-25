package com.happyaging.fallprevention.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.happyaging.fallprevention.exception.BaseExceptionHandler;
import com.happyaging.fallprevention.exception.ExceptionHandlerOrder;
import com.happyaging.fallprevention.exception.support.global.GlobalException;
import com.happyaging.fallprevention.util.api.ApiErrorResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(ExceptionHandlerOrder.GLOBAL_EXCEPTION_HANDLER)
public class GlobalExceptionHandler extends BaseExceptionHandler<GlobalException> {

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ApiErrorResult> handleCustomException(GlobalException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}
}
