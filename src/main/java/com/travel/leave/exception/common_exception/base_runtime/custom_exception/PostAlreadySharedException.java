package com.travel.leave.exception.common_exception.base_runtime.custom_exception;

import com.travel.leave.exception.common_exception.base_runtime.CustomException;
import com.travel.leave.exception.enums.custom.post.PostExceptionMsg;

public class PostAlreadySharedException extends CustomException {
    public PostAlreadySharedException(PostExceptionMsg postExceptionMsg) {
        super(postExceptionMsg.getMessage(), postExceptionMsg.getStatusCode());
    }
}