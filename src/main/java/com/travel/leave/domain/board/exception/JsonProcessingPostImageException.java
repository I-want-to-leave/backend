package com.travel.leave.domain.board.exception;

import com.travel.leave.domain.board.exception.enums.PostImageExceptionMessage;
import com.travel.leave.exception.common_exception.base_runtime.JsonParsingException;
import lombok.Getter;

@Getter
public class JsonProcessingPostImageException extends JsonParsingException {

    public JsonProcessingPostImageException(PostImageExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage(), exceptionMessage.getStatusCode());
    }
}