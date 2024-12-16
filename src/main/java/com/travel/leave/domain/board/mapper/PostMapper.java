package com.travel.leave.domain.board.mapper;

import com.travel.leave.domain.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostPreparationDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostResponseDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostTravelRouteDTO;
import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.travel.entity.Travel;

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
                .likeCount(likeCount)
                .images(images)
                .comments(comments)
                .preparations(preparations)
                .travelRoutes(travelRoutes)
                .build();
    }
}