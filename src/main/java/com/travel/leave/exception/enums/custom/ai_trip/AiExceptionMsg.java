package com.travel.leave.exception.enums.custom.ai_trip;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AiExceptionMsg {
    GOOGLE_MAPS_PLACE_NOT_FOUND(404, "파악할 수 없는 장소"),

    GPT_RESPONSE_PARSING_FAIL(500, "AI 생성 응답을 파싱하는 데 실패했습니다.");

    private final int statusCode;
    private final String message;
}