package com.travel.leave.board.mapper;

import com.travel.leave.board.dto.request.create.PreparationRequestDTO;
import com.travel.leave.board.dto.response.postdetail.PostPreparationDTO;
import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostPreparation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PostPreparationMapper {

    public static List<PostPreparation> toPostPreparationEntities(List<PreparationRequestDTO> preparationDTOs, Post post) {
        if (preparationDTOs == null || preparationDTOs.isEmpty()) {
            return Collections.emptyList();
        }

        return preparationDTOs.stream()
                .map(dto -> PostPreparation.builder()
                        .name(dto.getItemName())
                        .quantity(dto.getQuantity())
                        .post(post)
                        .build())
                .collect(Collectors.toList());
    }

    public static PostPreparationDTO toPostPreparationDTO(PostPreparation postPreparation) {
        return PostPreparationDTO.builder()
                .itemName(postPreparation.getName())
                .quantity(postPreparation.getQuantity())
                .build();
    }
}