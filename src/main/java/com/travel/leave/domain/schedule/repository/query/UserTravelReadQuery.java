package com.travel.leave.domain.schedule.repository.query;

import java.util.List;

public interface UserTravelReadQuery {
    List<String> findUsersInTravel(Long travelCode);
}
