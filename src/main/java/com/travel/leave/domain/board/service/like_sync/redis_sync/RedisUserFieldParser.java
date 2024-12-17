package com.travel.leave.domain.board.service.like_sync.redis_sync;

import com.travel.leave.domain.board.board_enum.LikeSyncExceptionMessage;
import org.springframework.stereotype.Component;

@Component
public class RedisUserFieldParser {

    public Long extractUserCodeFromRedisField(Object userField) {
        if (userField == null) {
            throw new IllegalArgumentException(LikeSyncExceptionMessage.REDIS_EXTRACT_USER_FIELD_FAIL.getMessage());
        }

        String[] parts = userField.toString().split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException(LikeSyncExceptionMessage.REDIS_WRONG_USER_FILED_FORMAT.getMessage() + userField);
        }

        try {
            return Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(LikeSyncExceptionMessage.REDIS_EXTRACT_USER_FIELD_FAIL.getMessage() + userField, e);
        }
    }
}