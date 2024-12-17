package com.travel.leave.domain.board.board_enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisLikeField {
    COUNT("count"),
    USER_PREFIX("user:"),
    REDIS_POST_KEY("post:likes:"),
    REDIS_POST_KEYS("post:likes:*");

    private final String value;
}