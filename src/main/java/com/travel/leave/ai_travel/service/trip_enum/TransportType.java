package com.travel.leave.ai_travel.service.trip_enum;

import lombok.Getter;

@Getter
public enum TransportType {
    DRIVING("차"),
    WALKING("도보"),
    BICYCLING("자전거"),
    TRANSIT("대중교통"),
    FERRY("배");

    private final String description;

    TransportType(String description) {
        this.description = description;
    }
}