package com.happyaging.fallprevention.exception;

public interface ExceptionHandlerOrder {
	int BUSINESS_EXCEPTION_HANDLER = 1;
	int SECURITY_EXCEPTION_HANDLER = 2;
	int GLOBAL_EXCEPTION_HANDLER = 3;
	int VALIDATE_EXCEPTION_HANDLER = 4;
	int EXTERNAL_EXCEPTION_HANDLER = 5;
}
