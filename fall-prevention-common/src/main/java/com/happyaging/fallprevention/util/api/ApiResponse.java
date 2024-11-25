package com.happyaging.fallprevention.util.api;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ApiResponse {
    public static <T> ApiSuccessResult<T> success(HttpStatus httpStatus) {
        return new ApiSuccessResult<>(httpStatus.value(), null);
    }

    public static <T> ApiSuccessResult<T> success(HttpStatus httpStatus, T response) {
        return new ApiSuccessResult<>(httpStatus.value(), response);
    }

    public static ApiErrorResult error(HttpStatus status, String msg) {
        return new ApiErrorResult(status.value(), msg);
    }
}
