package com.travel.leave.domain.ai_travel.dto.ai_recommend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "AI 여행 준비물 추천 품목", example = "물병, 워킹화")
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedItemDTO {
    @Schema(description = "추천 아이템 목록", example = "물병, 워킹화")
    private String itemName;

    @Schema(description = "각 추천 아이템 갯수", example = "1")
    private int quantity;
}