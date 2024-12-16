package com.travel.leave.board.mapper;

import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostPreparation;
import com.travel.leave.board.entity.PostTravelRoute;
import com.travel.leave.login.entity.UserTravel;
import com.travel.leave.ai_travel.entity.*;

public class SyncTripMapper {

    public static Travel toTravelEntity(Post post, String representativeImageUrl) {
        return Travel.builder()
                .name(post.getPostTitle())
                .content(post.getPostContent())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .imageUrl(representativeImageUrl)
                .build();
    }

    public static TravelLocation toTravelLocationEntity(PostTravelRoute route, Long travelCode) {
        return TravelLocation.builder()
                .travelCode(travelCode)
                .scheduleDetails(ScheduleDetails.builder()
                        .name(route.getPlaceName())
                        .startTime(route.getStartAt())
                        .endTime(route.getEndAt())
                        .step(route.getStepOrder())
                        .geographicDetails(GeographicDetails.builder()
                                .latitude(route.getLatitude())
                                .longitude(route.getLongitude())
                                .build())
                        .build())
                .build();
    }

    public static TravelPreparation toTravelPreparationEntity(PostPreparation postPreparation, Long travelCode) {
        return TravelPreparation.builder()
                .travelCode(travelCode)
                .name(postPreparation.getName())
                .quantity(postPreparation.getQuantity())
                .content("")
                .build();
    }

    public static UserTravel toUserTravelEntity(Long travelCode, Long userCode) {
        return UserTravel.builder()
                .travelCode(travelCode)
                .userCode(userCode)
                .build();
    }
}