package com.travel.leave.exception.common_exception.base_runtime.redis_exception;

import com.travel.leave.exception.common_exception.base_runtime.RedisException;
import com.travel.leave.exception.enums.RedisExceptionMsg;

public class RedisParsingException extends RedisException {

    public RedisParsingException(RedisExceptionMsg exceptionMessage) {
        super(exceptionMessage.getMessage(), exceptionMessage.getStatusCode());
    }
}
