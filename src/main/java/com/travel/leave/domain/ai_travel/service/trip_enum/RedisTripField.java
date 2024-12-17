package com.travel.leave.domain.ai_travel.service.trip_enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisTripField {

    REDIS_AI_TRIP_KEY("AI_tripPlan:"),
    REDIS_CACHE_FAIL("캐시 데이터가 소실 되었습니다. 다시 시도 하십시오"),
    REDIS_CACHE_SAVE_FAIL("캐시 저장 실패"),
    CACHE_FIND_FAIL_MAIN_IMAGE("대표 이미지를 찾을 수 없습니다");
    private final String message;
}
