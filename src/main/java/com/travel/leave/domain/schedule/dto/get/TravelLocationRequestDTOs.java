package com.travel.leave.domain.schedule.dto.get;

import java.time.LocalDate;
import java.util.List;

public record TravelLocationRequestDTOs(
    String date,
    List<TravelLocationRequestDTO> timelines
) {

}
