package com.travel.leave.domain.board.service.post_image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.leave.domain.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.exception.common_exception.base_runtime.redis_exception.RedisDeserializationException;
import com.travel.leave.exception.common_exception.base_runtime.redis_exception.RedisSerializationException;
import com.travel.leave.exception.enums.RedisExceptionMsg;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CachePostImageManager {

    private final RedisTemplate<String, String> redisTemplate;
    private static final long TTL = 10 * 60;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CachePostImageManager(@Qualifier("dtoRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String getCacheKey(Long postCode) {
        return RedisImageField.REDIS_POST_IMAGE_KEY.getValue() + postCode;
    }

    public List<PostImageDTO> getImages(Long postCode) {
        String key = getCacheKey(postCode);
        String cachedData = redisTemplate.opsForValue().get(key);

        if (cachedData != null) {
            redisTemplate.expire(key, TTL, TimeUnit.SECONDS);
            return deserialize(cachedData);
        }
        return null;
    }

    public void putImages(Long postCode, List<PostImageDTO> listPostImageDTO) {
        String key = getCacheKey(postCode);
        String serializedData = serialize(listPostImageDTO);
        redisTemplate.opsForValue().set(key, serializedData, TTL, TimeUnit.SECONDS);
    }

    private String serialize(List<PostImageDTO> images) {
        try {
            return objectMapper.writeValueAsString(images);
        } catch (JsonProcessingException e) {
            throw new RedisSerializationException(RedisExceptionMsg.REDIS_SERIALIZATION_ERROR);
        }
    }

    private List<PostImageDTO> deserialize(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RedisDeserializationException(RedisExceptionMsg.REDIS_DESERIALIZATION_ERROR);
        }
    }
}