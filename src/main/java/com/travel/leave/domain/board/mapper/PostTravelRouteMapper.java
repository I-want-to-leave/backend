package com.travel.leave.domain.board.mapper;

import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.posttravelroute.entity.PostTravelRoute;
import com.travel.leave.subdomain.travellocaion.entity.ScheduleDetails;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PostTravelRouteMapper {

    public static List<PostTravelRoute> toPostTravelRouteEntities(List<TravelLocation> travelLocations, Post post) {
        if (travelLocations == null || travelLocations.isEmpty()) {
            return Collections.emptyList();
        }

        return travelLocations.stream()
                .map(tl -> {
                    ScheduleDetails details = tl.getScheduleDetails();
                    return PostTravelRoute.builder()
                            .placeName(details.getName())
                            .startAt(details.getStartTime())
                            .endAt(details.getEndTime())
                            .stepOrder(details.getStep())
                            .latitude(details.getGeographicDetails().getLatitude())
                            .longitude(details.getGeographicDetails().getLongitude())
                            .post(post)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
