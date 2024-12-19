package com.travel.leave.domain.board.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LikeSyncExceptionMessage {

    REDIS_EXTRACT_USER_FIELD_FAIL(400, "User field 는 null 일 수 없습니다"),
    REDIS_WRONG_USER_FILED_FORMAT(422, "잘못된 user field 형식입니다:"),
    REDIS_WRONG_POSTCODE_FORMAT(422, "잘못된 postCode 형식입니다: ");

    private final int statusCode;
    private final String message;
}