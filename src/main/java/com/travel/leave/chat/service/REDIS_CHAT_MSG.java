package com.travel.leave.chat.service;

import lombok.Getter;

@Getter
public enum REDIS_CHAT_MSG {
    ACTIVE_USERS_LIST("activeUsers:");

    private final String message;
    REDIS_CHAT_MSG(String message) {
        this.message = message;
    }
}
