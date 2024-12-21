package com.travel.leave.exception.common_exception.base_runtime.redis_exception;

import com.travel.leave.exception.common_exception.base_runtime.RedisException;
import com.travel.leave.exception.enums.RedisExceptionMsg;
import lombok.Getter;

@Getter
public class RedisDeserializationException extends RedisException {

    public RedisDeserializationException(RedisExceptionMsg exceptionMessage) {
        super(exceptionMessage.getMessage(), exceptionMessage.getStatusCode());
    }
}