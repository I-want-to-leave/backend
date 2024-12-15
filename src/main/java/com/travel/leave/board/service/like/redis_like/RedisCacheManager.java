package com.travel.leave.board.service.like.redis_like;

import com.travel.leave.board.entity.PostLike;
import com.travel.leave.board.service.enums.RedisField;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class RedisCacheManager {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheManager(@Qualifier("HashRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cachePostLikes(String redisKey, Long likeCount, List<PostLike> postLikes) {
        for (PostLike postLike : postLikes) {
            String userField = RedisField.USER_PREFIX.getValue() + postLike.getUserCode();
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            redisTemplate.opsForHash().put(redisKey, userField, currentTimeMillis);
        }
        redisTemplate.opsForHash().put(redisKey, RedisField.COUNT.getValue(), String.valueOf(likeCount));
        redisTemplate.expire(redisKey, Duration.ofDays(3));
    }
}