package com.travel.leave.domain.ai_travel.exception;

import com.travel.leave.exception.common_exception.base_runtime.CustomException;

public class GoogleMapsResponseException extends CustomException {
    public GoogleMapsResponseException(AiExceptionMessage aiExceptionMessage) {
        super(aiExceptionMessage.getMessage(), aiExceptionMessage.getStatusCode());
    }
}