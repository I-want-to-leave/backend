package com.travel.leave.schedule.dto.get.invite;

public record UserInviteDTO(
        Long userCode,
        String email,
        String name
) {
}
