package com.travel.leave.board.dto.response.postdetail;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostTravelRouteDTO {
    private String placeName;
    private Timestamp startAt;
    private Timestamp endAt;
    private Integer stepOrder;
    private BigDecimal latitude;
    private BigDecimal longitude;
}