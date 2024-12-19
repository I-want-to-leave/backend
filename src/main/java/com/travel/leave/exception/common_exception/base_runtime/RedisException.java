package com.travel.leave.exception.common_exception.base_runtime;

import com.travel.leave.exception.common_exception.BaseRuntimeException;

public class RedisException extends BaseRuntimeException {
    public RedisException(String message, int statusCode) {
        super(message, statusCode);
    }
}