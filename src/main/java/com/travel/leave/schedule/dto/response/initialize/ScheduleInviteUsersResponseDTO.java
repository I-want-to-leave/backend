package com.travel.leave.schedule.dto.response.initialize;

import java.util.List;

public record ScheduleInviteUsersResponseDTO(
        List<ScheduleInviteUserResponseDTO> scheduleInviteUserResponseDTOs
) {
}
