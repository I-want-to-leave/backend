package com.travel.leave.domain.ai_travel.service.trip_enum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Schema(description = "여행 경로에서 사용되는 교통수단")
@RequiredArgsConstructor
public enum TransportType {
    @Schema(description = "차")
    DRIVING("차"),

    @Schema(description = "도보")
    WALKING("도보"),

    @Schema(description = "자전거")
    BICYCLING("자전거"),

    @Schema(description = "대중교통")
    TRANSIT("대중교통"),

    @Schema(description = "배")
    FERRY("배");

    private final String description;
}