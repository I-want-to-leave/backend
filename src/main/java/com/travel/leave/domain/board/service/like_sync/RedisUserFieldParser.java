package com.travel.leave.domain.board.service.like_sync;

import com.travel.leave.domain.board.exception.RedisUserFieldException;
import com.travel.leave.domain.board.exception.enums.LikeSyncExceptionMessage;
import org.springframework.stereotype.Component;

@Component
public class RedisUserFieldParser {

    public Long extractUserCodeFromRedisField(Object userField) {
        if (userField == null) {
            throw new RedisUserFieldException(LikeSyncExceptionMessage.REDIS_EXTRACT_USER_FIELD_FAIL);
        }

        String[] parts = userField.toString().split(":");
        if (parts.length < 2) {
            throw new RedisUserFieldException(LikeSyncExceptionMessage.REDIS_WRONG_USER_FILED_FORMAT);
        }

        try {
            return Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            throw new RedisUserFieldException(LikeSyncExceptionMessage.REDIS_EXTRACT_USER_FIELD_FAIL);
        }
    }
}
