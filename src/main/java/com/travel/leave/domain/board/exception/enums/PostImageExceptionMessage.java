package com.travel.leave.domain.board.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostImageExceptionMessage {

    REDIS_SERIALIZATION_ERROR(500, "Redis 데이터 직렬화 실패"),
    REDIS_DESERIALIZATION_ERROR(500, "Redis 데이터 역직렬화 실패"),
    CACHE_FIND_FAIL_MAIN_IMAGE(404, "대표 이미지를 찾을 수 없습니다"),
    CACHE_FIND_FAIL_SORTED_IMAGE(404, "지정된 순서의 이미지를 찾을 수 없습니다");

    private final int statusCode;
    private final String message;
}