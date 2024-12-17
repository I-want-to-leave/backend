package com.travel.leave.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // HTTP 상태 코드 설정
public class PostAuthorConflictException extends RuntimeException {
    public PostAuthorConflictException(String message) {
        super(message);
    }
}
