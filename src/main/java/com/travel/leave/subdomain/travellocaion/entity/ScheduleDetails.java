package com.travel.leave.subdomain.travellocaion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDetails {
    @Column(name = "travel_location_name")
    private String name;

    @Column(name = "travel_location_content")
    private String content;

    @Column(name = "travel_location_start_time")
    private Timestamp startTime;

    @Column(name = "travel_location_end_time")
    private Timestamp endTime;

    @Column(name = "travel_location_step")
    private Integer step;

    @Embedded
    private GeographicDetails geographicDetails;

    public static ScheduleDetails of(String name, String content, Timestamp startTime, Timestamp endTime, Integer step, GeographicDetails geographicDetails) {
        return new ScheduleDetails(name, content, startTime, endTime, step, null);
    }
}