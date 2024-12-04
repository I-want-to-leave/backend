package com.travel.leave.mypage.dto;

public record MyPageInfoCountResponseDTO(
        int postCount,
        int commentCount,
        int travelCount
) {
}
