package com.travel.leave.subdomain.post.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostExceptionMessage {

    TRAVEL_NOT_FOUND("삭제되거나 등록되지 않은 여행입니다"),
    POST_ALREADY_SHARED("이미 공유된 여행입니다");

    private final String message;
}
