package com.travel.leave.domain.board.exception;

import com.travel.leave.domain.board.exception.enums.LikeSyncExceptionMessage;
import com.travel.leave.exception.common_exception.base_runtime.RedisException;

public class RedisUserFieldException extends RedisException {
    public RedisUserFieldException(LikeSyncExceptionMessage errorMessage) {
        super(errorMessage.getMessage(), errorMessage.getStatusCode());
    }
}