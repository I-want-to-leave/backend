package com.travel.leave.domain.schedule.repository.query;

import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;

import java.util.List;

public interface TravelLocationQuery {
    List<TravelLocation> findTravelLocations(Long travelCode);
}
