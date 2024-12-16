package com.travel.leave.schedule.dto.get;

import java.time.LocalDate;
import java.util.List;

public record TravelLocationRequestDTOs(
    LocalDate date,
    List<TravelLocationRequestDTO> timelines
) {

}
