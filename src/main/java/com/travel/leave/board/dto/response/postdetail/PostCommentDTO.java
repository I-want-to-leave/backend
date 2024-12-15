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
    private Long postCode;
    private String nickname;

    public PostCommentDTO(Long code, String content, Timestamp createdAt, Long postCode, String nickname) {
        this.code = code;
        this.content = content;
        this.createdAt = createdAt;
        this.postCode = postCode;
        this.nickname = nickname;
    }
}
