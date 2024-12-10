package com.travel.leave.mypage.dto;

public record MyPageInfoCountsResponseDTO(
        int postCount,
        int commentCount,
        int travelCount
) {
}
