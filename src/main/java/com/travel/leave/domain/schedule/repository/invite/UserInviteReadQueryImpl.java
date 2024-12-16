package com.travel.leave.domain.schedule.repository.invite;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.leave.domain.schedule.dto.get.invite.UserInviteDTO;
import com.travel.leave.domain.schedule.dto.get.invite.UserInviteDTOs;
import com.travel.leave.subdomain.user.entity.QUser;
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
