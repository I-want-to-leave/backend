package com.travel.leave.exception.common_exception.base_runtime.redis_exception;

import com.travel.leave.exception.common_exception.base_runtime.RedisException;
import com.travel.leave.exception.enums.RedisExceptionMsg;
import lombok.Getter;

@Getter
public class RedisSerializationException extends RedisException {

    public RedisSerializationException(RedisExceptionMsg exceptionMessage) {
        super(exceptionMessage.getMessage(), exceptionMessage.getStatusCode());
    }
}