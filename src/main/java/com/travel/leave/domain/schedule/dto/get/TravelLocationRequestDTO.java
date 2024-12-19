package com.travel.leave.domain.schedule.dto.get;

public record TravelLocationRequestDTO(
        Long code,
        String time,
        String title,
        String description
) {
}
