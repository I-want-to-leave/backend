package com.travel.leave.travel.dto.ai;

import com.travel.leave.travel.TransportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LatLngDTO {
    private String placeName;
    private double lat;
    private double lng;
    private TransportType transportType;
}
