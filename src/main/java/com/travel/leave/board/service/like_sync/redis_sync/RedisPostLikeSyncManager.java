package com.travel.leave.board.service.like_sync.redis_sync;

import com.travel.leave.board.service.enums.BOARD_EX_MSG;
import com.travel.leave.board.service.enums.RedisField;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisPostLikeSyncManager {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPostLikeSyncManager(
            @Qualifier("HashRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Set<String> getRedisKeys() {
        return redisTemplate.keys(RedisField.REDIS_POST_KEYS.getValue());
    }

    public Long extractPostCodeFromKey(String redisKey) {
        try {
            String postCode = redisKey.split(":")[2];
            return Long.parseLong(postCode);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(BOARD_EX_MSG.REDIS_WRONG_POSTCODE_FORMAT.getMessage() + redisKey);
        }
    }

    public Set<Object> getRedisUserFields(String redisKey) {
        Set<Object> userFields = redisTemplate.opsForHash().keys(redisKey);
        userFields.remove(RedisField.COUNT.getValue());
        return userFields;
    }
}