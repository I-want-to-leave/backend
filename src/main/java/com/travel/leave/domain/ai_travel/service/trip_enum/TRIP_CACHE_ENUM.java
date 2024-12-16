package com.travel.leave.domain.ai_travel.service.trip_enum;

import lombok.Getter;

@Getter
public enum TRIP_CACHE_ENUM {

    REDIS_AI_TRIP_KEY("AI_tripPlan:");
    private final String field;
    TRIP_CACHE_ENUM(String field) {
        this.field = field;
    }
}
