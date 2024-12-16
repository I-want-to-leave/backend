package com.travel.leave.domain.schedule.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.leave.login.entity.QUser;
import com.travel.leave.login.entity.QUserTravel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserTravelReadQueryImpl implements UserTravelReadQuery{
    private final JPAQueryFactory jpaQueryFactory;
    private final QUser qUser = QUser.user;
    private final QUserTravel qUserTravel = QUserTravel.userTravel;

    @Override
    public List<String> findUsersInTravel(Long travelCode) {
        return jpaQueryFactory.select(qUser.nickname)
                .from(qUserTravel)
                .where(qUserTravel.travelCode.eq(travelCode))
                .leftJoin(qUser).on(qUserTravel.userCode.eq(qUser.code))
                .fetch();
    }
}
