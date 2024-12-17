package com.travel.leave.domain.board.service.like_sync.redis_sync;

import com.travel.leave.domain.board.board_enum.RedisLikeField;
import com.travel.leave.domain.board.board_enum.LikeSyncExceptionMessage;
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
        return redisTemplate.keys(RedisLikeField.REDIS_POST_KEYS.getValue());
    }

    public Long extractPostCodeFromKey(String redisKey) {
        try {
            String postCode = redisKey.split(":")[2];
            return Long.parseLong(postCode);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(LikeSyncExceptionMessage.REDIS_WRONG_POSTCODE_FORMAT.getMessage() + redisKey);
        }
    }

    public Set<Object> getRedisUserFields(String redisKey) {
        Set<Object> userFields = redisTemplate.opsForHash().keys(redisKey);
        userFields.remove(RedisLikeField.COUNT.getValue());
        return userFields;
    }
}