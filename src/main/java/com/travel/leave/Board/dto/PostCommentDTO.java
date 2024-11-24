package com.travel.leave.Board.dto;

import com.travel.leave.Board.entity.PostComment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCommentDTO {
    private Long commentCode;
    private String content;
    private java.sql.Timestamp createdAt;
    private Long userCode;

    public static PostCommentDTO fromEntity(PostComment comment) {
        return PostCommentDTO.builder()
                .commentCode(comment.getCode())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .userCode(comment.getUserCode())
                .build();
    }
}

