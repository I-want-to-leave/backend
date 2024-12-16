package com.travel.leave.ai_travel.service.trip_enum;

import lombok.Getter;

@Getter
public enum GOOGLE_MSG_ENUM {

    GEOCODING_PARAM_ADDRESS("address"),
    GEOCODING_PARAM_KEY("key");

    private final String message;
    GOOGLE_MSG_ENUM(String message) {
        this.message = message;
    }
}
