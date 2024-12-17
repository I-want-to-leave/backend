package com.travel.leave.subdomain.postlike.service;

import com.travel.leave.subdomain.postlike.repository.PostLikeRepository;
import com.travel.leave.domain.board.validator.aop.aop_annotation.EnsureRedisData;
import com.travel.leave.domain.board.service.like.RedisKeyManager;
import com.travel.leave.domain.board.service.like.RedisPostLikeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final RedisKeyManager redisKeyManager;
    private final RedisPostLikeManager redisPostLikeManager;

    @Transactional @EnsureRedisData
    public void toggleLike(Long postCode, Long userCode) {
        String redisKey = redisKeyManager.generateRedisKey(postCode);
        if (isLikedByUser(postCode, userCode)) {
            redisPostLikeManager.unlikePost(redisKey, userCode, postCode);
        } else {
            redisPostLikeManager.likePost(redisKey, userCode, postCode);
        }
    }

    @Transactional(readOnly = true) @EnsureRedisData
    public boolean isLikedByUser(Long postCode, Long userCode) {
        String redisKey = redisKeyManager.generateRedisKey(postCode);
        return redisPostLikeManager.isLiked(redisKey, userCode);
    }

    @Transactional(readOnly = true) @EnsureRedisData
    public Long getLikesCount(Long postCode) {
        String redisKey = redisKeyManager.generateRedisKey(postCode);
        try {
            return redisPostLikeManager.getLikesCount(redisKey);
        } catch (RedisConnectionFailureException | RedisSystemException ex) {
            log.warn("Redis 에 접근이 불가하니 체크 부탁하며 좋아요 조회는 DB 에서 조회합니다: {}", ex.getMessage());
            return postLikeRepository.countLikesByPostCode(postCode);
        }
    }
}