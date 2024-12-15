package com.travel.leave.travel.service;

import com.travel.leave.travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.travel.service.trip_enum.TRIP_CACHE_ENUM;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TripCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public TripCacheService(@Qualifier("gptRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveTripPlan(Long userCode, RecommendDTO tripPlan) {
        redisTemplate.opsForValue().set(TRIP_CACHE_ENUM.REDIS_AI_TRIP_KEY.getField() + userCode, tripPlan, Duration.ofMinutes(30));
    }

    public RecommendDTO getTripPlan(Long userCode) {
        return (RecommendDTO) redisTemplate.opsForValue().get(TRIP_CACHE_ENUM.REDIS_AI_TRIP_KEY.getField() + userCode);
    }

    public void deleteTripPlan(Long userCode) {
        redisTemplate.delete(TRIP_CACHE_ENUM.REDIS_AI_TRIP_KEY.getField() + userCode);
    }
}