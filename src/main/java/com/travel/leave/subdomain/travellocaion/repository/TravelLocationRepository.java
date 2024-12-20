package com.travel.leave.subdomain.travellocaion.repository;

import com.travel.leave.domain.schedule.repository.query.TravelLocationQuery;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelLocationRepository extends JpaRepository<TravelLocation, Long>, TravelLocationQuery {

    @Query("SELECT tl FROM TravelLocation tl " +
            "JOIN Travel t ON tl.travelCode = t.code " +
            "WHERE t.code = :travelCode AND t.deletedAt IS null ")
    List<TravelLocation> findByTravelCode(@Param("travelCode") Long travelCode);
    // 이따가 봐야 됨
}
