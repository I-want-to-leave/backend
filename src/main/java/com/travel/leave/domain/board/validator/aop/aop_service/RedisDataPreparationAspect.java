package com.travel.leave.domain.board.validator.aop.aop_service;

import com.travel.leave.subdomain.postlike.entity.PostLike;
import com.travel.leave.subdomain.postlike.repository.PostLikeRepository;
import com.travel.leave.domain.board.service.like.RedisCacheManager;
import com.travel.leave.domain.board.service.like.RedisKeyManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class RedisDataPreparationAspect {

    private final RedisKeyManager redisKeyManager;
    private final RedisCacheManager redisCacheManager;
    private final PostLikeRepository postLikeRepository;

    @Before("@annotation(com.travel.leave.domain.board.validator.aop.aop_annotation.EnsureRedisData) && args(postCode,..)")
    public void ensureRedisDataExists(Long postCode) {
        String redisKey = redisKeyManager.generateRedisKey(postCode);

        if (!redisKeyManager.hasKey(redisKey)) {
            Long dbLikeCount = postLikeRepository.countLikesByPostCode(postCode); // 해당 게시물 좋아요 수
            List<PostLike> postLikes = postLikeRepository.findAllByPostCode(postCode); // 해당 게시물 좋아요 데이터
            redisCacheManager.cachePostLikes(redisKey, dbLikeCount, postLikes);
        }
    }
}