package com.travel.leave.board.service.like.redis_like;

import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostLike;
import com.travel.leave.board.mapper.PostLikeMapper;
import com.travel.leave.board.repository.post_like.PostLikeRepository;
import com.travel.leave.board.service.enums.RedisField;
import com.travel.leave.board.service.like_scheduler.util.PostRecentCheckerUtils;
import com.travel.leave.board.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisPostLikeManager {

    private final PostLikeRepository postLikeRepository;
    private final PostValidator postValidator;
    private final PostRecentCheckerUtils postRecentCheckerUtils;
    private final RedisTemplate<String, Object> redisTemplate;

    public void unlikePost(String redisKey, Long userCode, Long postCode) {
        if (postRecentCheckerUtils.isPostOld(postCode)) {
            postLikeRepository.deleteByPostCodeAndUserCode(postCode, userCode);
            return;
        }
        String userField = RedisField.USER_PREFIX.getValue() + userCode;
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            redisTemplate.opsForHash().delete(redisKey, userField);
            redisTemplate.opsForHash().increment(redisKey, RedisField.COUNT.getValue(), -1);
            redisTemplate.expire(redisKey, Duration.ofDays(3));
            return null;
        });
    }

    public void likePost(String redisKey, Long userCode, Long postCode) {
        if (postRecentCheckerUtils.isPostOld(postCode)) {
            Post post = postValidator.validateActivePost(postCode);
            PostLike postLike = PostLikeMapper.toPostLikeEntity(post.getPostCode(), userCode);
            postLikeRepository.save(postLike);
            return;
        }

        String userField = RedisField.USER_PREFIX.getValue() + userCode;
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            redisTemplate.opsForHash().put(redisKey, userField, currentTimeMillis);
            redisTemplate.opsForHash().increment(redisKey, RedisField.COUNT.getValue(), 1);
            redisTemplate.expire(redisKey, Duration.ofDays(3));
            return null;
        });
    }

    public Long getLikesCount(String redisKey) {
        Object count = redisTemplate.opsForHash().get(redisKey, RedisField.COUNT.getValue());
        return count == null ? 0L : Long.parseLong((String) count);
    }

    public boolean isLiked(String redisKey, Long userCode) {
        String userField = RedisField.USER_PREFIX.getValue() + userCode;
        Boolean isLiked = redisTemplate.opsForHash().hasKey(redisKey, userField);
        return Boolean.TRUE.equals(isLiked);
    }
}