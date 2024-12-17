package com.travel.leave.domain.board.board_enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeSyncExceptionMessage {

    REDIS_EXTRACT_USER_FIELD_FAIL("User field 는 null 일 수 없습니다"),
    REDIS_WRONG_USER_FILED_FORMAT("잘못된 user field 형식:"),
    REDIS_WRONG_POSTCODE_FORMAT("잘못된 사용자 필드 형식입니다: ");

    private final String message;
}