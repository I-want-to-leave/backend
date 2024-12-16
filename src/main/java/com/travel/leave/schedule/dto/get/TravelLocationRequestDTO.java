package com.travel.leave.schedule.dto.get;

import java.time.LocalDateTime;

public record TravelLocationRequestDTO(
        Long code,
        LocalDateTime time,
        String title,
        String content
) {
}
