package com.travel.leave.board.service.like_scheduler.redis_scheduler;

import org.springframework.stereotype.Component;

@Component
public class RedisUserFieldParser {

    public Long extractUserCodeFromRedisField(Object userField) {
        if (userField == null) {
            throw new IllegalArgumentException("User field 는 null 일 수 없습니다.");
        }

        String[] parts = userField.toString().split(":");
        if (parts.length < 2) {
            throw new IllegalArgumentException("잘못된 user field 형식: " + userField);
        }

        try {
            return Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("User field 에서 잘못된 사용자 코드: " + userField, e);
        }
    }
}
