package com.travel.leave.domain.board.service.like;

import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.postlike.entity.PostLike;
import com.travel.leave.domain.board.mapper.PostLikeMapper;
import com.travel.leave.subdomain.postlike.repository.PostLikeRepository;
import com.travel.leave.domain.board.service.like_sync.utils.PostRecentCheckerUtils;
import com.travel.leave.domain.board.validator.common_validator.BoardValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisPostLikeManager {

    private final PostLikeRepository postLikeRepository;
    private final BoardValidator boardValidator;
    private final PostRecentCheckerUtils postRecentCheckerUtils;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPostLikeManager(
            PostLikeRepository postLikeRepository,
            BoardValidator boardValidator,
            PostRecentCheckerUtils postRecentCheckerUtils,
            @Qualifier("HashRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.postLikeRepository = postLikeRepository;
        this.boardValidator = boardValidator;
        this.postRecentCheckerUtils = postRecentCheckerUtils;
        this.redisTemplate = redisTemplate;
    }

    public void unlikePost(String redisKey, Long userCode, Long postCode) {
        if (postRecentCheckerUtils.isPostOld(postCode)) {
            postLikeRepository.deleteByPostCodeAndUserCode(postCode, userCode);
            return;
        }
        String userField = RedisLikeField.USER_PREFIX.getValue() + userCode;
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            redisTemplate.opsForHash().delete(redisKey, userField);
            redisTemplate.opsForHash().increment(redisKey, RedisLikeField.COUNT.getValue(), -1);
            redisTemplate.expire(redisKey, Duration.ofDays(3));
            return null;
        });
    }

    public void likePost(String redisKey, Long userCode, Long postCode) {
        if (postRecentCheckerUtils.isPostOld(postCode)) {
            Post post = boardValidator.validateActivePost(postCode);
            PostLike postLike = PostLikeMapper.toPostLikeEntity(post.getPostCode(), userCode);
            postLikeRepository.save(postLike);
            return;
        }

        String userField = RedisLikeField.USER_PREFIX.getValue() + userCode;
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            redisTemplate.opsForHash().put(redisKey, userField, currentTimeMillis);
            redisTemplate.opsForHash().increment(redisKey, RedisLikeField.COUNT.getValue(), 1);
            redisTemplate.expire(redisKey, Duration.ofDays(3));
            return null;
        });
    }

    public Long getLikesCount(String redisKey) {
        Object count = redisTemplate.opsForHash().get(redisKey, RedisLikeField.COUNT.getValue());
        return count == null ? 0L : Long.parseLong((String) count);
    }

    public boolean isLiked(String redisKey, Long userCode) {
        String userField = RedisLikeField.USER_PREFIX.getValue() + userCode;
        Boolean isLiked = redisTemplate.opsForHash().hasKey(redisKey, userField);
        return Boolean.TRUE.equals(isLiked);
    }
}