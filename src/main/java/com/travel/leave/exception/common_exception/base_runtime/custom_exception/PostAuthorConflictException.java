package com.travel.leave.exception.common_exception.base_runtime.custom_exception;

import com.travel.leave.exception.common_exception.base_runtime.CustomException;
import com.travel.leave.exception.enums.custom.post.PostExceptionMsg;

public class PostAuthorConflictException extends CustomException {
    public PostAuthorConflictException(PostExceptionMsg exceptionMessage) {
        super(exceptionMessage.getMessage(), exceptionMessage.getStatusCode());
    }
}