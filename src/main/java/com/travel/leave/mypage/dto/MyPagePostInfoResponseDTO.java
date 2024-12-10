package com.travel.leave.mypage.dto;

import java.sql.Timestamp;

public record MyPagePostInfoResponseDTO(
        Long postCode,
        String postTitle,
        Timestamp createdAt,
        Timestamp startDate,
        Timestamp endDate,
        String content
) {
}
