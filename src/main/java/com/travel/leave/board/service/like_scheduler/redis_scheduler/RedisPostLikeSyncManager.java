package com.travel.leave.board.service.like_scheduler.redis_scheduler;

import com.travel.leave.board.service.enums.RedisField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisPostLikeSyncManager {

    private final RedisTemplate<String, Object> redisTemplate;

    public Set<String> getRedisKeys() {
        return redisTemplate.keys(RedisField.REDIS_POST_KEYS.getValue());
    }

    public Long extractPostCodeFromKey(String redisKey) {
        try {
            String postCode = redisKey.split(":")[2];
            return Long.parseLong(postCode);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 사용자 필드 형식입니다: " + redisKey);
        }
    }

    public Set<Object> getRedisUserFields(String redisKey) {
        Set<Object> userFields = redisTemplate.opsForHash().keys(redisKey);
        userFields.remove(RedisField.COUNT.getValue());
        return userFields;
    }
}