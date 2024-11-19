package com.travel.leave.entity.travellocation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class GeographicDetails {
    @Column(name = "travel_location_longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "travel_location_latitude", precision = 9, scale = 6)
    private BigDecimal latitude;
}
