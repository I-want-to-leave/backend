package com.travel.leave.subdomain.postcomment.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommentExceptionMessage {

    USER_NOT_FOUND("사용자를 찾을 수 없습니다"),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다"),
    COMMENT_ACCESS_DENIED("댓글에 대한 권한이 없습니다."),
    INVALID_POST_COMMENT_RELATION("해당 게시글에 속하지 않는 댓글입니다.");


    private final String message;
}
