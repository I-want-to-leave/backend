package com.travel.leave.ai_travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeographicDetails {
    @Column(name = "travel_location_longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "travel_location_latitude", precision = 9, scale = 6)
    private BigDecimal latitude;
}
