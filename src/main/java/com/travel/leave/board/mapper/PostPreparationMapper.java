package com.travel.leave.board.mapper;

import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostPreparation;
import com.travel.leave.ai_travel.entity.TravelPreparation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PostPreparationMapper {

    public static List<PostPreparation> toPostPreparationEntities(List<TravelPreparation> travelPreparations, Post post) {
        if (travelPreparations == null || travelPreparations.isEmpty()) {
            return Collections.emptyList();
        }

        return travelPreparations.stream()
                .map(tp -> PostPreparation.builder()
                        .name(tp.getName())
                        .quantity(tp.getQuantity())
                        .post(post)
                        .build())
                .collect(Collectors.toList());
    }
}