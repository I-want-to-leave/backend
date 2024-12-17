package com.travel.leave.domain.ai_travel.service.trip_enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransportType {
    DRIVING("차"),
    WALKING("도보"),
    BICYCLING("자전거"),
    TRANSIT("대중교통"),
    FERRY("배");

    private final String description;
}