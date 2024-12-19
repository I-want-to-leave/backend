package com.travel.leave.config.swagger;

import lombok.Getter;

@Getter
public enum ApiResponseStatus {
    SUCCESS("200", "요청 성공"),
    BAD_REQUEST("400", "잘못된 요청 데이터"),
    NOT_FOUND("404", "데이터를 찾을 수 없음"),
    SERVER_ERROR("500", "내부 서버 오류");

    private final String code;
    private final String description;

    ApiResponseStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
