package com.travel.leave.domain.schedule.dto.get.invite;

public record UserInviteDTO(
        Long userCode,
        String email,
        String name
) {
}
