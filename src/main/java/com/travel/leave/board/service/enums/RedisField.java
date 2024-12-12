package com.travel.leave.board.service.enums;

import lombok.Getter;

@Getter
public enum RedisField {
    COUNT("count"),
    USER_PREFIX("user:"),
    REDIS_POST_KEY("post:likes:"),
    REDIS_POST_KEYS("post:likes:*");

    private final String value;

    RedisField(String value) {
        this.value = value;
    }
}