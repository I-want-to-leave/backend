package com.travel.leave.board.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequestDTO {
    private String placeName;
    private Timestamp startAt;
    private Timestamp endAt;
    private Integer stepOrder;
    private BigDecimal latitude;
    private BigDecimal longitude;
}