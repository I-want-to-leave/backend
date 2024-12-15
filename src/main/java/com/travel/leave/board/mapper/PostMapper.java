package com.travel.leave.board.mapper;

import com.travel.leave.board.dto.response.postdetail.*;
import com.travel.leave.board.entity.Post;
import com.travel.leave.travel.entity.Travel;

import java.util.List;

public class PostMapper {

    public static Post toPostEntity(Travel travel, Long userCode) {
        return Post.builder()
                .postTitle(travel.getName())
                .postContent(travel.getContent())
                .startDate(travel.getStartDate())
                .endDate(travel.getEndDate())
                .userCode(userCode)
                .travelCode(travel.getCode())
                .views(0L)
                .build();
    }

    public static PostResponseDTO toPostResponseDTO(
            Post post,
            Long likeCount,
            List<PostImageDTO> images,
            List<PostCommentDTO> comments,
            List<PostPreparationDTO> preparations,
            List<PostTravelRouteDTO> travelRoutes
    ) {
        return PostResponseDTO.builder()
                .postCode(post.getPostCode())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .startDate(post.getStartDate())
                .endDate(post.getEndDate())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .views(post.getViews())
                .userCode(post.getUserCode())
                .travelCode(post.getTravelCode())
                .likeCount(likeCount)
                .images(images)
                .comments(comments)
                .preparations(preparations)
                .travelRoutes(travelRoutes)
                .build();
    }
}