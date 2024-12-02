package com.travel.leave.board.dto;

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
}

