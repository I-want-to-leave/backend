package com.travel.leave.exception.common_exception.base_runtime.custom_exception;

import com.travel.leave.exception.common_exception.base_runtime.CustomException;
import com.travel.leave.exception.enums.custom.ai_trip.AiExceptionMsg;

public class GoogleMapsResponseException extends CustomException {
    public GoogleMapsResponseException(AiExceptionMsg aiExceptionMsg) {
        super(aiExceptionMsg.getMessage(), aiExceptionMsg.getStatusCode());
    }
}