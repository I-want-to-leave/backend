package com.travel.leave.domain.board.service.enums;

import lombok.Getter;

@Getter
public enum BOARD_EX_MSG {
    POST_NOT_FOUND("해당 게시물을 찾을 수 없습니다."),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다"),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),
    IMAGE_NOT_FOUND("이미지를 찾을 수 없습니다."),
    POST_ALREADY_SHARED("이미 공유된 여행입니다."),
    COMMENT_ACCESS_DENIED("댓글에 대한 권한이 없습니다."),
    INVALID_POST_COMMENT_RELATION("해당 게시글에 속하지 않는 댓글입니다."),
    DATABASE_SAVE_EXCEPTION("데이터베이스 저장 중 문제가 발생했습니다."),
    DATABASE_ACCESS_EXCEPTION("데이터베이스 접근 중 문제가 발생했습니다."),
    INVALID_SORT_FIELD("잘못된 정렬 키워드입니다. 지원되는 키워드는: likes, views, created_at 입니다."),
    ILLEGAL_ARGUMENT("요청 파라미터가 잘못되었습니다."),
    TRAVEL_NOT_FOUND("삭제되거나 등록되지 않은 여행입니다"),

    REDIS_SERIALIZATION_ERROR("Redis 데이터 직렬화 실패"),
    REDIS_DESERIALIZATION_ERROR("Redis 데이터 역직렬화 실패"),
    CACHE_FIND_FAIL_MAIN_IMAGE("대표 이미지를 찾을 수 없습니다"),
    CACHE_FIND_FAIL_SORTED_IMAGE("지정된 순서의 이미지를 찾을 수 없습니다"),
    REDIS_EXTRACT_USER_FIELD_FAIL("User field 는 null 일 수 없습니다"),
    REDIS_WRONG_USER_FILED_FORMAT("잘못된 user field 형식: "),
    REDIS_WRONG_POSTCODE_FORMAT("잘못된 사용자 필드 형식입니다: ");

    private final String message;
    BOARD_EX_MSG(String message) {
        this.message = message;
    }
}