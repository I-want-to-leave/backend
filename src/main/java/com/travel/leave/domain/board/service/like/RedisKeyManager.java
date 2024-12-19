package com.travel.leave.domain.board.service.like;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyManager {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REDIS_KEY_PREFIX = RedisLikeField.REDIS_POST_KEY.getValue();

    public RedisKeyManager(@Qualifier("HashRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateRedisKey(Long postCode) {
        return REDIS_KEY_PREFIX + postCode;
    }

    public boolean hasKey(String redisKey) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
    }
}