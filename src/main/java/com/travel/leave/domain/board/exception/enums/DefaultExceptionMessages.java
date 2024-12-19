package com.travel.leave.domain.board.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefaultExceptionMessages {

    TRAVEL_NOT_FOUND("여행을 찾을 수 없습니다"),

    // ========================================================================== //

    POST_NOT_FOUND("해당 게시물을 찾을 수 없습니다."),
    POST_CONTROL_ACCESS_DENIED("해당 게시물의 조작 권한이 없습니다."),

    // ========================================================================== //

    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다"),
    COMMENT_ACCESS_DENIED("댓글에 대한 권한이 없습니다.");

    private final String message;
}