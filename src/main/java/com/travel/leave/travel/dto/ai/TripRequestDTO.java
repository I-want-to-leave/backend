package com.travel.leave.travel.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripRequestDTO {
    private String startLocation;
    private String startDate;
    private String endDate;
    private String keywords;
    private boolean carOwned;
}