package com.travel.leave.exception.enums.custom.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostExceptionMsg {

    NICKNAME_NOT_FOUND(404,"닉네임을 찾지 못하였습니다"),

    POST_ALREADY_SHARED(400, "이미 공유된 여행입니다"),

    CANNOT_ADD_OWN_POST(403, "본인이 작성한 게시글은 자신의 여행 목록에 추가할 수 없습니다");

    private final int statusCode;
    private final String message;
}
