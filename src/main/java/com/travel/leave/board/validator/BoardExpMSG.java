package com.travel.leave.board.validator;

import lombok.Getter;

@Getter
public enum BoardExpMSG {
    POST_NOT_FOUND("해당 게시물을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),
    POST_ALREADY_SHARED("이미 공유된 여행입니다."),
    COMMENT_ACCESS_DENIED("댓글에 대한 권한이 없습니다."),
    INVALID_POST_COMMENT_RELATION("해당 게시글에 속하지 않는 댓글입니다."),
    DATABASE_SAVE_EXCEPTION("데이터베이스 저장 중 문제가 발생했습니다."),
    DATABASE_ACCESS_EXCEPTION("데이터베이스 접근 중 문제가 발생했습니다."),
    INVALID_SORT_FIELD("잘못된 정렬 키워드입니다. 지원되는 키워드는: likes, views, created_at 입니다."),
    ILLEGAL_ARGUMENT("요청 파라미터가 잘못되었습니다.");

    private final String message;

    BoardExpMSG(String message) {
        this.message = message;
    }
}