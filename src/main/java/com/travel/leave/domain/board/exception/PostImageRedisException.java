package com.travel.leave.domain.board.exception;

import com.travel.leave.domain.board.exception.enums.PostImageExceptionMessage;
import com.travel.leave.exception.common_exception.base_runtime.RedisException;
import lombok.Getter;

@Getter
public class PostImageRedisException extends RedisException {

    public PostImageRedisException(PostImageExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage(), exceptionMessage.getStatusCode());
    }
}