package com.travel.leave.domain.board.exception;

import com.travel.leave.domain.board.exception.enums.SyncTravelExceptionMessage;
import com.travel.leave.exception.common_exception.base_runtime.CustomException;

public class PostAuthorConflictException extends CustomException {
    public PostAuthorConflictException(SyncTravelExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage(), exceptionMessage.getStatusCode());
    }
}