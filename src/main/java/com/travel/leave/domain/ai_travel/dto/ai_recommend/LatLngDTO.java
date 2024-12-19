package com.travel.leave.domain.ai_travel.dto.ai_recommend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.leave.domain.ai_travel.service.trip_enum.TransportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Schema(description = "AI 여행 코스 일차 별 정보")
@AllArgsConstructor
@NoArgsConstructor
public class LatLngDTO {
    @Schema(description = "장소 이름", example = "버킹엄 궁전")
    private String placeName;

    @Schema(description = "위도", example = "51.501364")
    private double lat;

    @Schema(description = "경도", example = "-0.14189")
    private double lng;

    @Schema(description = "하루 별 코스 순서", example = "1")
    private int stepOrder;

    @Schema(description = "해당 장소 도착 시간", example = "2024-12-01 09:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp startAt;

    @Schema(description = "해당 장소 떠나는 시간", example = "2024-12-01 11:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp endAt;

    @Schema(description = "교통수단", implementation = TransportType.class)
    private TransportType transportType;
}