package com.travel.leave.board.service.like.redis_like;

import com.travel.leave.board.service.enums.RedisField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisKeyManager {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REDIS_KEY_PREFIX = RedisField.REDIS_POST_KEY.getValue();

    public String generateRedisKey(Long postCode) {
        return REDIS_KEY_PREFIX + postCode;
    }

    public boolean hasKey(String redisKey) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
    }
}