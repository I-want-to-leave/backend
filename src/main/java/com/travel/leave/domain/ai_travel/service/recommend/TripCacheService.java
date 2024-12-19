package com.travel.leave.domain.ai_travel.service.recommend;

import com.travel.leave.domain.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.domain.ai_travel.service.trip_enum.RedisTripField;
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
        String redisKey = RedisTripField.REDIS_AI_TRIP_KEY.getValue() + userCode;
        redisTemplate.opsForValue().set(redisKey, tripPlan, Duration.ofMinutes(30));
    }

    public RecommendDTO getTripPlan(Long userCode) {
        String redisKey = RedisTripField.REDIS_AI_TRIP_KEY.getValue() + userCode;
        return (RecommendDTO) redisTemplate.opsForValue().get(redisKey);
    }

    public void deleteTripPlan(Long userCode) {
        String redisKey = RedisTripField.REDIS_AI_TRIP_KEY.getValue() + userCode;
        redisTemplate.delete(redisKey);
    }
}
