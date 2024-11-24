package com.travel.leave.board.dto;

import java.sql.Timestamp;
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
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long views;
    private Long userCode;
    private Long likes;
    private List<PostCommentDTO> comments;

    public ResponseDetailPostDTO(Long postCode, String title, String content, Timestamp createdAt,
                                 Timestamp updatedAt, Long views, Long userCode, Long likes) {
        this.postCode = postCode;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.views = views;
        this.userCode = userCode;
        this.likes = likes;
    }
}