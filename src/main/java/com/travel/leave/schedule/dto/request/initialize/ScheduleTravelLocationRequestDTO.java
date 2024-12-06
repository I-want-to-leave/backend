package com.travel.leave.schedule.dto.request.initialize;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleTravelLocationRequestDTO(
        LocalDate travelDate,
        LocalTime startTime,
        LocalTime endTime,
        Integer step,
        String travelLocationName,
        String travelLocationContent
) {
}
