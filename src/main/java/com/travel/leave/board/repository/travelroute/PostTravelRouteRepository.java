package com.travel.leave.board.repository.travelroute;

import com.travel.leave.board.dto.response.postdetail.PostTravelRouteDTO;
import com.travel.leave.board.entity.PostTravelRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTravelRouteRepository extends JpaRepository<PostTravelRoute, Long> {

    @Query("""
    SELECT new com.travel.leave.board.dto.response.postdetail.PostTravelRouteDTO(
        ptr.placeName,
        ptr.startAt,
        ptr.endAt,
        ptr.stepOrder,
        ptr.latitude,
        ptr.longitude
    )
    FROM PostTravelRoute ptr
    WHERE ptr.post.postCode = :postCode
    """)
    List<PostTravelRouteDTO> findRoutesByPostCode(@Param("postCode") Long postCode);

}