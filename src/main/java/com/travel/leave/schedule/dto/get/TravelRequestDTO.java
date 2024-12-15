package com.travel.leave.schedule.dto.get;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record TravelRequestDTO(
        LocalDateTime startDate,
        LocalDateTime endDate,
        String title,
        String information,
        List<String> image,
        List<MemberRequestDTO> member,
        List<TravelLocationRequestDTOs> schedule
) {

    @Override
    public String toString() {
        return title + " - " + information + " - " + startDate + " - " + endDate + " - " + image.size() + " - " + member.size() + " - " + schedule.size();
    }
}
