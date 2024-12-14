package com.travel.leave.schedule.dto.get;

import java.sql.Timestamp;

public record TravelLocationRequestDTO(
        Long code,
        Timestamp time,
        String title,
        String content
) {
}
