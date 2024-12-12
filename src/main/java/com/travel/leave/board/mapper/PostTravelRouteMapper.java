package com.travel.leave.board.mapper;

import com.travel.leave.board.dto.request.create.RouteRequestDTO;
import com.travel.leave.board.dto.response.postdetail.PostTravelRouteDTO;
import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostTravelRoute;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PostTravelRouteMapper {

    public static List<PostTravelRoute> toPostTravelRouteEntities(List<RouteRequestDTO> routeRequestDTOS, Post post) {
        if (routeRequestDTOS == null || routeRequestDTOS.isEmpty()) {
            return Collections.emptyList();
        }

        return routeRequestDTOS.stream()
                .map(dto -> PostTravelRoute.builder()
                        .placeName(dto.getPlaceName())
                        .startAt(dto.getStartAt())
                        .endAt(dto.getEndAt())
                        .stepOrder(dto.getStepOrder())
                        .latitude(dto.getLatitude())
                        .longitude(dto.getLongitude())
                        .post(post)
                        .build())
                .collect(Collectors.toList());
    }

    public static PostTravelRouteDTO toPostTravelRouteDTO(PostTravelRoute postTravelRoute) {
        return PostTravelRouteDTO.builder()
                .placeName(postTravelRoute.getPlaceName())
                .startAt(postTravelRoute.getStartAt())
                .endAt(postTravelRoute.getEndAt())
                .stepOrder(postTravelRoute.getStepOrder())
                .latitude(postTravelRoute.getLatitude())
                .longitude(postTravelRoute.getLongitude())
                .build();
    }
}
