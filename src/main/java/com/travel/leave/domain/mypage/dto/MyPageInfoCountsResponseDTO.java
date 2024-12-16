package com.travel.leave.domain.mypage.dto;

public record MyPageInfoCountsResponseDTO(
        int postCount,
        int commentCount,
        int travelCount
) {
}
