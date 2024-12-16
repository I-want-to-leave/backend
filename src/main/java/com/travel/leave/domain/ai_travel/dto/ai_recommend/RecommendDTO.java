package com.travel.leave.domain.ai_travel.dto.ai_recommend;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}