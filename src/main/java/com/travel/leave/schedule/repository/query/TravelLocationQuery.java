package com.travel.leave.schedule.repository.query;

import com.travel.leave.ai_travel.entity.TravelLocation;

import java.util.List;

public interface TravelLocationQuery {
    List<TravelLocation> findTravelLocations(Long travelCode);
}