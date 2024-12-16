package com.travel.leave.subdomain.travelpreparation.repository;

import com.travel.leave.subdomain.travelpreparation.entity.TravelPreparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelPreparationRepository extends JpaRepository<TravelPreparation, Long> {

    @Query("SELECT tp FROM TravelPreparation tp " +
            "JOIN Travel t ON tp.travelCode = t.code " +
            "WHERE t.code = :travelCode AND t.deletedAt IS NULL")
    List<TravelPreparation> findByTravelCode(@Param("travelCode") Long travelCode);

    List<TravelPreparation> findAllByTravelCodeAndIsDeletedIsFalse(Long travelCode);
}


