package com.travel.leave.mypage.dto;

import java.util.List;

public record MyPageTravelResponseDTO(
        Long travelCode,
        String travelName,
        String travelContent,
        List<String> userNicknames
) {
}
