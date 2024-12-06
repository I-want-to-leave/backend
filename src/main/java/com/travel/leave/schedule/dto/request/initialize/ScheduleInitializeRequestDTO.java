package com.travel.leave.schedule.dto.request.initialize;

import java.time.LocalDate;
import java.util.List;

public record ScheduleInitializeRequestDTO(
        LocalDate travelStartDate,
        LocalDate travelEndDate,
        String travelName,
        String travelContent,
        String travelImageUrl,
        List<ScheduleInvitedUserRequestDTO> invitedUserRequestDTOs,
        List<ScheduleTravelLocationRequestDTO> travelLocationRequestDTOs
) {
}
