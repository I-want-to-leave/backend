package com.travel.leave.exception;

import com.travel.leave.exception.message.ExceptionMessage;

public class UsernameDuplicationException extends RuntimeException {
    public UsernameDuplicationException(String duplicatedUsername) {
        super(ExceptionMessage.DUPLICATE_USERNAME.getMessageWithOneARG(duplicatedUsername));
    }
}
