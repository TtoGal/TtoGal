package com.ttogal.common.exception;

public record ExceptionResponse(
        int code,
        String message
) {
}