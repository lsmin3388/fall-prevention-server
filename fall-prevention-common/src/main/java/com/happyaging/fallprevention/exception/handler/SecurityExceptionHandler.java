package com.happyaging.fallprevention.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.happyaging.fallprevention.exception.BaseExceptionHandler;
import com.happyaging.fallprevention.exception.ExceptionHandlerOrder;
import com.happyaging.fallprevention.exception.support.security.ForbiddenException;
import com.happyaging.fallprevention.exception.support.security.SecurityException;
import com.happyaging.fallprevention.exception.support.security.UnauthorizedException;
import com.happyaging.fallprevention.util.api.ApiErrorResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(ExceptionHandlerOrder.SECURITY_EXCEPTION_HANDLER)
public class SecurityExceptionHandler extends BaseExceptionHandler<SecurityException> {

	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<ApiErrorResult> handleSecurityException(SecurityException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiErrorResult> handleAuthenticationFailedException(UnauthorizedException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ApiErrorResult> handleForbiddenException(ForbiddenException exception) {
		return handleException(exception, exception.getHttpStatus(), exception.getErrorMsg());
	}
}
