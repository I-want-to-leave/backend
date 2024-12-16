package com.travel.leave.schedule.controller.socket;

import lombok.Getter;

@Getter
public enum ServiceType {
    UPDATE_TRAVEL_LOCATION("updateTravelLocation"),
    UPDATE_TRAVEL_CONTENT("updateTravelContent"),
    UPDATE_TRAVEL_LOCATION_GEOGRAPHIC("updateTravelGeoGraphic"),
    UPDATE_TRAVEL_PREPARATION("updateTravelPreparation"),
    UPDATE_TRAVEL_NAME("updateTravelName");

    private final String methodName;

    ServiceType(String methodName) {
        this.methodName = methodName;
    }

}
