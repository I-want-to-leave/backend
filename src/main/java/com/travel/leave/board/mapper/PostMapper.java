package com.travel.leave.board.mapper;

import com.travel.leave.board.dto.request.create.CreatePostRequestDTO;
import com.travel.leave.board.dto.response.postdetail.*;
import com.travel.leave.board.entity.Post;

import java.util.List;

public class PostMapper {

    public static Post toPostEntity(CreatePostRequestDTO createPostRequestDTO, Long userCode, Long travelCode) {
        return Post.builder()
                .postTitle(createPostRequestDTO.getTitle())
                .postContent(createPostRequestDTO.getContent())
                .startDate(createPostRequestDTO.getStartDate())
                .endDate(createPostRequestDTO.getEndDate())
                .userCode(userCode)
                .travelCode(travelCode)
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