package com.travel.leave.domain.board.exception;

import com.travel.leave.exception.common_exception.base_runtime.CustomException;
import com.travel.leave.domain.board.exception.enums.PostExceptionMessage;

public class PostAlreadySharedException extends CustomException {
    public PostAlreadySharedException(PostExceptionMessage postExceptionMessage) {
        super(postExceptionMessage.getMessage(), postExceptionMessage.getStatusCode());
    }
}