package com.travel.leave.travel.dto.ai_recommend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.leave.travel.service.trip_enum.TransportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LatLngDTO {
    private String placeName;
    private double lat;
    private double lng;
    private int stepOrder;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp endAt;
    private TransportType transportType;
}