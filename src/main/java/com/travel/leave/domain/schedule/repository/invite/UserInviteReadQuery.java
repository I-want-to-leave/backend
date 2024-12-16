package com.travel.leave.domain.schedule.repository.invite;

import com.travel.leave.domain.schedule.dto.get.invite.UserInviteDTOs;

public interface UserInviteReadQuery {
    UserInviteDTOs findScheduleInviteUsers(String emailKeyword, int size);
}
