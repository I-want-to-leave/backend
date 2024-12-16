package com.travel.leave.domain.mypage.dto;

import java.time.LocalDate;
import java.util.List;

public record MyPageTravelResponseDTO(
        Long travelCode,
        String travelName,
        String travelContent,
        List<String> userNicknames,
        String imageUrl,
        LocalDate startDate,
        LocalDate endDate
) {
}
