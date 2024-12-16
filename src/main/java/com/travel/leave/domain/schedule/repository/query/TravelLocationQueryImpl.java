package com.travel.leave.domain.schedule.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.leave.subdomain.travellocaion.entity.QTravelLocation;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class TravelLocationQueryImpl implements TravelLocationQuery{
    private final JPAQueryFactory jpaQueryFactory;
    private final QTravelLocation qTravelLocation = QTravelLocation.travelLocation;
    @Override
    public List<TravelLocation> findTravelLocations(Long travelCode) {
        return jpaQueryFactory.selectFrom(qTravelLocation)
                .where(qTravelLocation.travelCode.eq(travelCode))
                .orderBy(
                        qTravelLocation.scheduleDetails.startTime.asc(),
                        qTravelLocation.scheduleDetails.step.asc()
                )
                .fetch();
    }
}
