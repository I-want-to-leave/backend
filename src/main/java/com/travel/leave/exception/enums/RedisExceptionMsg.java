package com.travel.leave.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisExceptionMsg {

    REDIS_SERIALIZATION_ERROR(500, "Redis 데이터 직렬화 실패"),

    REDIS_DESERIALIZATION_ERROR(500, "Redis 데이터 역직렬화 실패"),

    CACHE_FIND_ERROR(404, "Redis 캐싱 데이터가 소실 되었습니다"),

    REDIS_PARSING_ERROR(500,"Redis 에서 반환된 데이터가 예상한 형식이 아닙니다");

    private final int statusCode;
    private final String message;
}
