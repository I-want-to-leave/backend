package com.travel.leave.travel.service;

import com.travel.leave.travel.dto.ai_recommend.RecommendDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TripCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveTripPlan(Long userCode, RecommendDTO tripPlan) {
        redisTemplate.opsForValue().set("AI_tripPlan:" + userCode, tripPlan, Duration.ofMinutes(30));
    }

    public RecommendDTO getTripPlan(Long userCode) {
        return (RecommendDTO) redisTemplate.opsForValue().get("AI_tripPlan:" + userCode);
    }

    public void deleteTripPlan(Long userCode) {
        redisTemplate.delete("AI_tripPlan:" + userCode);
    }
}