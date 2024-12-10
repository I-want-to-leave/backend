package com.travel.leave.travel.entity;

import com.travel.leave.schedule.dto.request.initialize.ScheduleTravelLocationRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDetails {
    @Column(name = "travel_location_name")
    private String name;

    @Column(name = "travel_location_content")
    private String content;

    @Column(name = "travel_location_start_at")
    private Timestamp startTime;

    @Column(name = "travel_location_end_at")
    private Timestamp endTime;

    @Column(name = "travel_location_step")
    private Integer step;

    @Embedded
    private GeographicDetails geographicDetails;

    public static ScheduleDetails of(ScheduleTravelLocationRequestDTO travelLocationRequestDTO,
                                     Timestamp startTime,
                                     Timestamp endTime){

        return new ScheduleDetails(
                travelLocationRequestDTO.travelLocationName(),
                travelLocationRequestDTO.travelLocationContent(),
                startTime,
                endTime,
                travelLocationRequestDTO.step(),
                null
        );
    }
}