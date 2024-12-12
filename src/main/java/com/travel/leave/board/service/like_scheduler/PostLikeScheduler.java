package com.travel.leave.board.service.like_scheduler;

import com.travel.leave.board.service.like_scheduler.redis_scheduler.RedisPostLikeSyncManager;
import com.travel.leave.board.service.like_scheduler.util.PostRecentCheckerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostLikeScheduler {

    private final RedisPostLikeSyncManager redisPostLikeSyncManager;
    private final SyncLikesToDBManager syncLikesToDBManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PostRecentCheckerUtils postRecentCheckerUtils;

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void syncAndCleanRedisData() {
        Set<String> keys = redisPostLikeSyncManager.getRedisKeys();
        if (keys == null || keys.isEmpty()) {
            return;
        }

        keys.forEach(key -> {
            try {
                Long postCode = redisPostLikeSyncManager.extractPostCodeFromKey(key);
                Set<Object> userFields = redisPostLikeSyncManager.getRedisUserFields(key);
                syncLikesToDBManager.syncLikesToDB(postCode, userFields);
                if (postRecentCheckerUtils.isPostOld(postCode)) {
                    redisTemplate.delete(key);
                }
            } catch (Exception e) {
                log.error("키 처리 중 오류 발생: {} - {}", key, e.getMessage());
            }
        });
    }
}