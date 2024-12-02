package com.travel.leave.board.service;

import com.travel.leave.board.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    /*
    private final RedisTemplate<String, Object> redisTemplate;
    private final PostLikeRepository postLikeRepository;

    private static final String POST_LIKE_KEY_PREFIX = "post:likes:";

    @Transactional
    public Integer toggleLike(Long postCode, Long userCode) {
        String redisKey = POST_LIKE_KEY_PREFIX + postCode;

        if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))) {
            List<Long> userCodes = postLikeRepository.findUserCodesByPostCode(postCode);
            redisTemplate.opsForSet().add(redisKey, userCodes.toArray());
        }

        Boolean isLiked = redisTemplate.opsForSet().isMember(redisKey, userCode);

        if (Boolean.TRUE.equals(isLiked)) {
            redisTemplate.opsForSet().remove(redisKey, userCode);
        } else {
            redisTemplate.opsForSet().add(redisKey, userCode);
        }

        Long likeCount = redisTemplate.opsForSet().size(redisKey);
        return likeCount != null ? likeCount.intValue() : 0;
    }
     */
}