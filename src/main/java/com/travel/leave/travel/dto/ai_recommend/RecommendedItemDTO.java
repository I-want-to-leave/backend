package com.travel.leave.travel.dto.ai_recommend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedItemDTO {
    private String itemName;
    private int quantity;
}