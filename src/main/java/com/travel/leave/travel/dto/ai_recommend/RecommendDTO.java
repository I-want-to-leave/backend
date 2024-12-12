package com.travel.leave.travel.dto.ai_recommend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendDTO {
    private String tripName;
    private String tripExplanation;
    private String imageUrl;
    private int totalCost;
    private List<RecommendedItemDTO> recommendedItems;
    private List<List<LatLngDTO>> dailyRoutes;
    private Date startDate;
    private Date endDate;
}