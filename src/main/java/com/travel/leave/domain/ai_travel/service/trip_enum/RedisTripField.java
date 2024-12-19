package com.travel.leave.domain.ai_travel.service.trip_enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisTripField {

    REDIS_AI_TRIP_KEY("AI_tripPlan:");
    private final String value;
}
