package com.travel.leave.board.mapper;

import com.travel.leave.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostComment;

public class PostCommentMapper {

    public static PostComment toPostCommentEntity(Post post, Long userCode, String content) {
        return PostComment.builder()
                .post(post)
                .userCode(userCode)
                .content(content)
                .build();
    }

    public static PostCommentDTO toPostCommentDTO(PostComment postComment) {
        return PostCommentDTO.builder()
                .code(postComment.getCode())
                .content(postComment.getContent())
                .createdAt(postComment.getCreatedAt())
                .userCode(postComment.getUserCode())
                .postCode(postComment.getPost().getPostCode())
                .build();
    }
}