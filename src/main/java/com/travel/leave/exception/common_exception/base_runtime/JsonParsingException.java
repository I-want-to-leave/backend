package com.travel.leave.exception.common_exception.base_runtime;

import com.travel.leave.exception.common_exception.BaseRuntimeException;

public class JsonParsingException extends BaseRuntimeException {
    public JsonParsingException(String message, int statusCode) {
        super(message, statusCode);
    }
}