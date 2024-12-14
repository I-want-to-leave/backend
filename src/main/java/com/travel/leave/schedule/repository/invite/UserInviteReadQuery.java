package com.travel.leave.schedule.repository.invite;

import com.travel.leave.schedule.dto.get.invite.UserInviteDTOs;

public interface UserInviteReadQuery {
    UserInviteDTOs findScheduleInviteUsers(String emailKeyword, int size);
}
