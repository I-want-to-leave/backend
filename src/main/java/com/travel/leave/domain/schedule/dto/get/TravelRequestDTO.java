package com.travel.leave.domain.schedule.dto.get;

import java.util.List;

public record TravelRequestDTO(
        String startDate,
        String endDate,
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
