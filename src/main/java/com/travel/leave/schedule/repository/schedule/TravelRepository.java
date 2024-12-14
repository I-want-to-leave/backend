package com.travel.leave.schedule.repository.schedule;

import com.travel.leave.travel.entity.Travel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long> {
}
