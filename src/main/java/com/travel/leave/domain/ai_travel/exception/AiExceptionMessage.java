package com.travel.leave.domain.ai_travel.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AiExceptionMessage {

    REDIS_CACHE_FAIL(500, "캐시 데이터가 소실 되었습니다. 다시 시도 하십시오"),
    REDIS_CACHE_SAVE_FAIL(500, "캐시 저장 실패"),
    GOOGLE_MAPS_PLACE_NOT_FOUND(404, "파악할 수 없는 장소"),
    GPT_RESPONSE_PARSING_FAIL(500, "AI 생성 응답을 파싱하는 데 실패했습니다.");

    private final int statusCode;
    private final String message;
}