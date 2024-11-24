package com.travel.leave.travel.entity;

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
    private Timestamp startAt;

    @Column(name = "travel_location_end_at")
    private Timestamp endTime;

    @Column(name = "travel_location_step")
    private Integer step;

    @Embedded
    private GeographicDetails geographicDetails;
}
