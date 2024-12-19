package com.travel.leave.domain.schedule.repository.query;

import com.travel.leave.domain.schedule.dto.get.invite.UserInviteDTOs;

public interface UserInviteReadQuery {
    UserInviteDTOs findScheduleInviteUsers(String emailKeyword, int size);
}
