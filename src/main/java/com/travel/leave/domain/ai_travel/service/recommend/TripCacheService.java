package com.travel.leave.domain.ai_travel.service.recommend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.domain.ai_travel.service.trip_enum.RedisTripField;
import com.travel.leave.exception.common_exception.base_runtime.redis_exception.RedisDeserializationException;
import com.travel.leave.exception.common_exception.base_runtime.redis_exception.RedisParsingException;
import com.travel.leave.exception.enums.RedisExceptionMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
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
        Object rawValue = redisTemplate.opsForValue().get(redisKey);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            if (rawValue instanceof String) {
                return objectMapper.readValue((String) rawValue, RecommendDTO.class);
            } else {
                throw new RedisParsingException(RedisExceptionMsg.REDIS_PARSING_ERROR);
            }

        } catch (Exception e) {
            throw new RedisDeserializationException(RedisExceptionMsg.REDIS_DESERIALIZATION_ERROR);
        }
    }

    public void deleteTripPlan(Long userCode) {
        String redisKey = RedisTripField.REDIS_AI_TRIP_KEY.getValue() + userCode;
        redisTemplate.delete(redisKey);
    }
}
