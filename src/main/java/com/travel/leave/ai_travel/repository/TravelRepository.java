package com.travel.leave.ai_travel.repository;

import com.travel.leave.ai_travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query("SELECT t FROM Travel t WHERE t.code = :travelCode AND t.deletedAt IS NULL")
    Optional<Travel> findActiveTravelById(@Param("travelCode") Long travelCode);
}