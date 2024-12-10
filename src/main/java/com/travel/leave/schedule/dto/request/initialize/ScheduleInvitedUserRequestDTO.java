package com.travel.leave.schedule.dto.request.initialize;

public record ScheduleInvitedUserRequestDTO(
        Long userCode,
        String name,
        String email
) {
}
