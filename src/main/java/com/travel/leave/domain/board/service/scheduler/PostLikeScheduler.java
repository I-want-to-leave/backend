package com.travel.leave.domain.board.service.scheduler;

import com.travel.leave.domain.board.service.like_sync.SyncLikesToDBManager;
import com.travel.leave.domain.board.service.like_sync.RedisPostLikeSyncManager;
import com.travel.leave.domain.board.service.like_sync.PostRecentCheckerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Slf4j
public class PostLikeScheduler {

    private final RedisPostLikeSyncManager redisPostLikeSyncManager;
    private final SyncLikesToDBManager syncLikesToDBManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PostRecentCheckerUtils postRecentCheckerUtils;

    public PostLikeScheduler(
            RedisPostLikeSyncManager redisPostLikeSyncManager,
            SyncLikesToDBManager syncLikesToDBManager,
            @Qualifier("HashRedisTemplate") RedisTemplate<String, Object> redisTemplate,
            PostRecentCheckerUtils postRecentCheckerUtils) {
        this.redisPostLikeSyncManager = redisPostLikeSyncManager;
        this.syncLikesToDBManager = syncLikesToDBManager;
        this.redisTemplate = redisTemplate;
        this.postRecentCheckerUtils = postRecentCheckerUtils;
    }

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