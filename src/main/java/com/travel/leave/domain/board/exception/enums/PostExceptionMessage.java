package com.travel.leave.domain.board.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostExceptionMessage {

    NICKNAME_NOT_FOUND(404,"닉네임을 찾지 못하였습니다"),
    POST_ALREADY_SHARED(400, "이미 공유된 여행입니다");

    private final int statusCode;
    private final String message;
}
