package com.travel.leave.domain.board.validator.common_validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ValidatorExceptionMessage {

    POST_NOT_FOUND("해당 게시물을 찾을 수 없습니다."),
    POST_CONTROL_ACCESS_DENIED("해당 게시물의 조작 권한이 없습니다.");

    private final String message;
}
