package com.travel.leave.schedule.dto.get;

import java.sql.Timestamp;
import java.util.List;

public record TravelRequestDTO(
        Timestamp startDate,
        Timestamp endDate,
        String title,
        String information,
        List<String> image,
        List<MemberRequestDTO> member,
        List<TravelLocationRequestDTOs> schedule
) {}
