package com.travel.leave.mypage.dto;

import java.sql.Timestamp;

public record MyPageCommentInfoResponseDTO(
    Long commentCode,
    String content,
    Timestamp createdAt,
    Long postCode,
    String postTitle
) {
}