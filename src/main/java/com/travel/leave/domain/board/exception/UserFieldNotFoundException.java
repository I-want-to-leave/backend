package com.travel.leave.domain.board.exception;

import com.travel.leave.domain.board.exception.enums.PostExceptionMessage;
import com.travel.leave.exception.common_exception.base_runtime.CustomException;

public class UserFieldNotFoundException extends CustomException {
    public UserFieldNotFoundException(PostExceptionMessage postExceptionMessage) {
        super(postExceptionMessage.getMessage(), postExceptionMessage.getStatusCode());
    }
}