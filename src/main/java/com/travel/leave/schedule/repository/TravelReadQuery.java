package com.travel.leave.schedule.repository;

import com.travel.leave.schedule.dto.response.initialize.ScheduleInviteUsersResponseDTO;

public interface TravelReadQuery {
    ScheduleInviteUsersResponseDTO findScheduleInviteUsers(String emailKeyword, int size);
}
