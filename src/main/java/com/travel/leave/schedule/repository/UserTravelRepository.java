package com.travel.leave.schedule.repository;

import com.travel.leave.entity.UserTravel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTravelRepository extends JpaRepository<UserTravel, Long> {
}
