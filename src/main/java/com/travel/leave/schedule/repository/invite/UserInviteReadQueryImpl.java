package com.travel.leave.schedule.repository.invite;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.leave.login.entity.QUser;
import com.travel.leave.schedule.dto.get.invite.UserInviteDTO;
import com.travel.leave.schedule.dto.get.invite.UserInviteDTOs;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInviteReadQueryImpl implements UserInviteReadQuery {
    private final JPAQueryFactory jpaQueryFactory;

    private final QUser qUser = QUser.user;

    public UserInviteDTOs findScheduleInviteUsers(String keyword, int size) {
        return new UserInviteDTOs(jpaQueryFactory.select(Projections.fields(
                UserInviteDTO.class,
                qUser.code.as("userCode"),
                qUser.nickname.as("name"),
                qUser.email.as("email")))
                .from(qUser)
                .where(qUser.email.startsWith(keyword))
                .limit(size)
                .fetch());
    }
}
