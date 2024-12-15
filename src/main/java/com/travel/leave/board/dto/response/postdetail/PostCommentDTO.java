package com.travel.leave.board.dto.response.postdetail;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PostCommentDTO {
    private Long code;
    private String content;
    private Timestamp createdAt;
    private Long userCode;
    private Long postCode;

    public PostCommentDTO(Long code, String content, Timestamp createdAt, Long userCode, Long postCode) {
        this.code = code;
        this.content = content;
        this.createdAt = createdAt;
        this.userCode = userCode;
        this.postCode = postCode;
    }
}
