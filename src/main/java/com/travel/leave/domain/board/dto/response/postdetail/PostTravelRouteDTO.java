package com.travel.leave.domain.board.dto.response.postdetail;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PostTravelRouteDTO {
    private String placeName;
    private Timestamp startAt;
    private Timestamp endAt;
    private Integer stepOrder;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public PostTravelRouteDTO(String placeName, Timestamp startAt, Timestamp endAt, Integer stepOrder, BigDecimal latitude, BigDecimal longitude) {
        this.placeName = placeName;
        this.startAt = startAt;
        this.endAt = endAt;
        this.stepOrder = stepOrder;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
