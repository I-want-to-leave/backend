package com.travel.leave.ai_travel.service;

import com.travel.leave.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.ai_travel.service.trip_enum.TRIP_CACHE_ENUM;
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
        String redisKey = TRIP_CACHE_ENUM.REDIS_AI_TRIP_KEY.getField() + userCode;
        redisTemplate.opsForValue().set(redisKey, tripPlan, Duration.ofMinutes(30));
        System.out.println("TripPlan saved with key: " + redisKey); // 디버깅 로그 추가
    }

    public RecommendDTO getTripPlan(Long userCode) {
        String redisKey = TRIP_CACHE_ENUM.REDIS_AI_TRIP_KEY.getField() + userCode;
        System.out.println("Fetching TripPlan with key: " + redisKey); // 디버깅 로그 추가
        return (RecommendDTO) redisTemplate.opsForValue().get(redisKey);
    }

    public void deleteTripPlan(Long userCode) {
        String redisKey = TRIP_CACHE_ENUM.REDIS_AI_TRIP_KEY.getField() + userCode;
        redisTemplate.delete(redisKey);
    }
}
