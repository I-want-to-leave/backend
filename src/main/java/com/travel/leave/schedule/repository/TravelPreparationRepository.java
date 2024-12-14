package com.travel.leave.schedule.repository;

import com.travel.leave.travel.entity.TravelPreparation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPreparationRepository extends JpaRepository<TravelPreparation, Long> {
    List<TravelPreparation> findAllByTravelCodeAndIsDeletedIsFalse(Long travelCode);
}
