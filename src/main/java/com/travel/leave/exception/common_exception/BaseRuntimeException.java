package com.travel.leave.exception.common_exception;

import lombok.Getter;

@Getter
public class BaseRuntimeException extends RuntimeException {

    public BaseRuntimeException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    private final int statusCode;
}
