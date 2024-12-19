package com.travel.leave.domain.schedule.dto.get;

import java.time.LocalDateTime;

public record TravelLocationRequestDTO(
        Long code,
        String startDate,
        String title,
        String description
) {
}
