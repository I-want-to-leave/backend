package com.travel.leave.travel.repository;

import com.travel.leave.schedule.repository.TravelReadQuery;
import com.travel.leave.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long> , TravelReadQuery {
}
