package com.travel.leave.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeographicDetails {
    @Column(name = "travel_location_longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "travel_location_latitude", precision = 9, scale = 6)
    private BigDecimal latitude;
}
