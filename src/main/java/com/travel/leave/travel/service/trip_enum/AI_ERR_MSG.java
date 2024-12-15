package com.travel.leave.travel.service.trip_enum;

import lombok.Getter;

@Getter
public enum AI_ERR_MSG {

    REDIS_CACHE_FAIL("캐시 데이터가 소실 되었습니다. 다시 시도 하십시오"),
    REDIS_CACHE_SAVE_FAIL("캐시 저장 실패"),
    CACHE_FIND_FAIL_MAIN_IMAGE("대표 이미지를 찾을 수 없습니다");

    private final String message;
    AI_ERR_MSG(String message) {
        this.message = message;
    }
}