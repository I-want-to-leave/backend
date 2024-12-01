package com.travel.leave.board.service;

import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostLike;
import com.travel.leave.board.repository.PostLikeRepository;
import com.travel.leave.board.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeSyncService {
    /*

    private final RedisTemplate<String, Object> redisTemplate;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    private static final String POST_LIKE_KEY_PREFIX = "post:likes:";

    @Scheduled(fixedRate = 60000)
    public void syncLikesToDatabase() {
        Set<String> redisKeys = redisTemplate.keys(POST_LIKE_KEY_PREFIX + "*");

        if (redisKeys != null) {
            for (String redisKey : redisKeys) {
                Long postCode = Long.parseLong(redisKey.replace(POST_LIKE_KEY_PREFIX, ""));
                Post post = postRepository.findById(postCode)
                        .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

                Set<Object> redisUserCodes = redisTemplate.opsForSet().members(redisKey);
                List<Long> dbUserCodes = postLikeRepository.findUserCodesByPostCode(postCode);

                if (redisUserCodes != null) {
                    for (Object redisUserCode : redisUserCodes) {
                        Long userCodeLong = Long.parseLong(redisUserCode.toString());
                        if (!dbUserCodes.contains(userCodeLong)) {
                            postLikeRepository.save(new PostLike(null, post, userCodeLong));
                        }
                    }
                }

                for (Long dbUserCode : dbUserCodes) {
                    if (redisUserCodes == null || !redisUserCodes.contains(dbUserCode)) {
                        postLikeRepository.deleteByPostAndUserCode(post, dbUserCode);
                    }
                }
            }
        }
    }
    */
}

