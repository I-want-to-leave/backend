package com.travel.leave.schedule.dto.response.initialize;

public record ScheduleInviteUserResponseDTO(
        Long userCode,
        String name,
        String email
) {
}
