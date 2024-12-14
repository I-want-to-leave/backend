package com.travel.leave.travel.repository;

import com.travel.leave.travel.entity.TravelLocation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelLocationRepository extends JpaRepository<TravelLocation, Long> {
    List<TravelLocation> findAllByTravelCode(Long travelCode);
}
