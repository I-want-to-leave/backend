package com.travel.leave.domain.board.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisField {
    COUNT("count"),
    USER_PREFIX("user:"),
    REDIS_POST_KEY("post:likes:"),
    REDIS_POST_KEYS("post:likes:*"),
    REDIS_POST_IMAGE_KEY("postImageCache:"),
    REDIS_POST_IMAGE_KEYS("postImageCache:*");

    private final String value;
}