package com.travel.leave.domain.ai_travel.exception;

import com.travel.leave.exception.common_exception.base_runtime.JsonParsingException;

public class GPTResponseParsingException extends JsonParsingException {
    public GPTResponseParsingException(AiExceptionMessage aiExceptionMessage) {
        super(aiExceptionMessage.getMessage(), aiExceptionMessage.getStatusCode());
    }
}