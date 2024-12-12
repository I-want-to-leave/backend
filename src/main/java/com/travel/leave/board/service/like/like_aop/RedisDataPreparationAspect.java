package com.travel.leave.board.service.like.like_aop;

import com.travel.leave.board.entity.PostLike;
import com.travel.leave.board.repository.post_like.PostLikeRepository;
import com.travel.leave.board.service.like.redis_like.RedisCacheManager;
import com.travel.leave.board.service.like.redis_like.RedisKeyManager;
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

    @Before("@annotation(com.travel.leave.board.service.like.like_aop.EnsureRedisData) && args(postCode,..)")
    public void ensureRedisDataExists(Long postCode) {
        String redisKey = redisKeyManager.generateRedisKey(postCode);

        if (!redisKeyManager.hasKey(redisKey)) {
            Long dbLikeCount = postLikeRepository.countLikesByPostCode(postCode);
            List<PostLike> postLikes = postLikeRepository.findAllByPostCode(postCode);
            redisCacheManager.cachePostLikes(redisKey, dbLikeCount, postLikes);
        }
    }
}