package com.travel.leave.Board.dto;

import com.travel.leave.Board.entity.Post;
import com.travel.leave.Board.entity.PostComment;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDetailPostDTO {
    private Long postCode;
    private String title;
    private String content;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;
    private Long views;
    private Long userCode;
    private Long likes;
    private List<PostCommentDTO> comments;

    public static ResponseDetailPostDTO fromEntity(Post post, Long likes, List<PostComment> comments) {
        return ResponseDetailPostDTO.builder()
                .postCode(post.getPostCode())
                .title(post.getPostTitle())
                .content(post.getPostContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .views(post.getViews())
                .userCode(post.getUserCode())
                .likes(likes)
                .comments(comments.stream().map(PostCommentDTO::fromEntity).toList())
                .build();
    }
}

