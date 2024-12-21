package com.travel.leave.domain.board.service.like_sync;

import com.travel.leave.exception.enums.custom.post.DefaultPostExceptionMsg;
import org.springframework.stereotype.Component;

@Component
public class RedisUserFieldParser {

    public Long extractUserCodeFromRedisField(Object userField) {
        if (userField == null) {
            throw new IllegalArgumentException(DefaultPostExceptionMsg.POST_REDIS_PARSING_FAIL.getMessage());
        }

        String[] parts = userField.toString().split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException(DefaultPostExceptionMsg.POST_REDIS_PARSING_FAIL.getMessage());
        }

        try {
            return Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(DefaultPostExceptionMsg.POST_REDIS_PARSING_FAIL.getMessage());
        }
    }
}
