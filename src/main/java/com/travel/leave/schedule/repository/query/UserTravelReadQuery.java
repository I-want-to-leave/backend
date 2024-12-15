package com.travel.leave.schedule.repository.query;

import com.travel.leave.entity.UserTravel;

import java.util.List;

public interface UserTravelReadQuery {
    List<String> findUsersInTravel(Long travelCode);
}
