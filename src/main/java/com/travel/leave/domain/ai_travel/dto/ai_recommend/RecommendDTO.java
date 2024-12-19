package com.travel.leave.domain.ai_travel.dto.ai_recommend;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "AI 추천 여행 계획 응답 DTO")
public class RecommendDTO {

    @Schema(description = "여행 이름", example = "런던 2박 3일 여행 여행")
    private String tripName;

    @Schema(description = "여행 설명", example = "런던의 주요 관광지를 방문하는 힐링 코스")
    private String tripExplanation;

    @Schema(description = "이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "총 비용 (달러 기준)", example = "800")
    private int totalCost;

    @Schema(description = "추천 아이템 목록")
    private List<RecommendedItemDTO> recommendedItems;

    @Schema(description = "일별 여행 경로")
    private List<List<LatLngDTO>> dailyRoutes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "여행 시작 날짜", example = "2024-12-20")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "여행 종료 날짜", example = "2024-12-23")
    private LocalDate endDate;
}