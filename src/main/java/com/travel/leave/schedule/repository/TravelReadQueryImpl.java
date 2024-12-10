package com.travel.leave.schedule.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.leave.entity.QUser;
import com.travel.leave.schedule.dto.response.initialize.ScheduleInviteUserResponseDTO;
import com.travel.leave.schedule.dto.response.initialize.ScheduleInviteUsersResponseDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TravelReadQueryImpl implements TravelReadQuery {
    private final JPAQueryFactory jpaQueryFactory;

    private final QUser qUser = QUser.user;

    public ScheduleInviteUsersResponseDTO findScheduleInviteUsers(String keyword, int size) {
        return new ScheduleInviteUsersResponseDTO(jpaQueryFactory.select(Projections.fields(
                ScheduleInviteUserResponseDTO.class,
                qUser.code.as("userCode"),
                qUser.nickname.as("name"),
                qUser.email.as("email")))
                .from(qUser)
                .where(qUser.email.startsWith(keyword))
                .limit(size)
                .fetch());
    }
}
