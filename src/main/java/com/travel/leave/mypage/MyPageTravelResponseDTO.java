package com.travel.leave.mypage;

import java.sql.Timestamp;
import java.util.List;

public record MyPageTravelResponseDTO(
        Long travelCode,
        String name,
        String content,
        Timestamp createdAt,
        String userNicknames
) {
}
