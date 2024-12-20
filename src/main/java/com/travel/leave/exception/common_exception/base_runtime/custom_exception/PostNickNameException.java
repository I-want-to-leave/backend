package com.travel.leave.exception.common_exception.base_runtime.custom_exception;

import com.travel.leave.exception.enums.custom.post.PostExceptionMsg;
import com.travel.leave.exception.common_exception.base_runtime.CustomException;

public class PostNickNameException extends CustomException {
    public PostNickNameException(PostExceptionMsg postExceptionMsg) {
        super(postExceptionMsg.getMessage(), postExceptionMsg.getStatusCode());
    }
}