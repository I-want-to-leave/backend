package com.travel.leave.board.dto.response.postdetail;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCommentDTO {
    private Long code;
    private String content;
    private Timestamp createdAt;
    private Long userCode;
    private Long postCode;
}