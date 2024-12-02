package com.travel.leave.board.mapper;

import com.travel.leave.board.dto.PostCommentDTO;
import com.travel.leave.board.entity.PostComment;

public class PostCommentMapper {

    public static PostCommentDTO fromPostCommentDTO(PostComment comment) {
        return PostCommentDTO.builder()
                .commentCode(comment.getCode())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .userCode(comment.getUserCode())
                .build();
    } // 댓글 엔티티를 댓글 DTO 로 변환 -> 게시물 상세 정보 조회시 stream 으로 사용
}