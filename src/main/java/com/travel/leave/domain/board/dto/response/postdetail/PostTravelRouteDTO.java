package com.travel.leave.domain.board.dto.response.postdetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Schema(description = "게시글 여행 경로 응답 DTO")
public class PostTravelRouteDTO {

    @Schema(description = "장소 이름", example = "에펠탑")
    private String placeName;

    @Schema(description = "여행 시작 시간", example = "2024-12-19T10:00:00.000Z")
    private Timestamp startAt;

    @Schema(description = "여행 종료 시간", example = "2024-12-19T12:00:00.000Z")
    private Timestamp endAt;

    @Schema(description = "여행 단계 순서", example = "1")
    private Integer stepOrder;

    @Schema(description = "위도", example = "48.858844")
    private BigDecimal latitude;

    @Schema(description = "경도", example = "2.294351")
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
