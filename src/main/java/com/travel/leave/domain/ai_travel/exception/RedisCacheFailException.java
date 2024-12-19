package com.travel.leave.domain.ai_travel.exception;

import com.travel.leave.exception.common_exception.base_runtime.RedisException;
import lombok.Getter;

@Getter
public class RedisCacheFailException extends RedisException {
    public RedisCacheFailException(AiExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage(), exceptionMessage.getStatusCode());
    }
}
