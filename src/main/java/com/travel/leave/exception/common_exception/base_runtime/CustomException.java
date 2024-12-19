package com.travel.leave.exception.common_exception.base_runtime;

import com.travel.leave.exception.common_exception.BaseRuntimeException;

public class CustomException extends BaseRuntimeException {
    public CustomException(String message, int statusCode) {
        super(message, statusCode);
    }
}