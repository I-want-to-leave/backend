package com.travel.leave.travel.entity;

import com.travel.leave.schedule.dto.get.TravelLocationRequestDTOs;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "travel_location")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TravelLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_location_code")
    private Long travelLocationCode;

    @Embedded
    private ScheduleDetails scheduleDetails;

    @Column(name = "travel_code")
    private Long travelCode;

    public static TravelLocation of(Long travelCode, ScheduleDetails scheduleDetails) {
        return new TravelLocation(null, scheduleDetails, travelCode);
    }
}
