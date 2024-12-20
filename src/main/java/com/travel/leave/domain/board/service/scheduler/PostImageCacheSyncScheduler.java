package com.travel.leave.domain.board.service.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.leave.domain.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.exception.common_exception.base_runtime.redis_exception.RedisDeserializationException;
import com.travel.leave.domain.board.mapper.PostImageMapper;
import com.travel.leave.domain.board.service.post_image.RedisImageField;
import com.travel.leave.exception.enums.RedisExceptionMsg;
import com.travel.leave.subdomain.postimage.entity.PostImage;
import com.travel.leave.subdomain.postimage.repository.PostImageRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class PostImageCacheSyncScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final PostImageRepository postImageRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PostImageCacheSyncScheduler(
            @Qualifier("dtoRedisTemplate") RedisTemplate<String, String> redisTemplate,
            PostImageRepository postImageRepository) {
        this.redisTemplate = redisTemplate;
        this.postImageRepository = postImageRepository;
    }

    @Scheduled(fixedRate = 300000)
    public void syncCacheToDB() {
        Set<String> keys = redisTemplate.keys(RedisImageField.REDIS_POST_IMAGE_KEYS.getValue());
        if (keys != null) {
            for (String key : keys) {
                Long postCode = extractPostCodeFromKey(key);
                String cachedData = redisTemplate.opsForValue().get(key);

                if (cachedData != null) {
                    List<PostImageDTO> cachedImages = deserialize(cachedData);
                    postImageRepository.deleteByPostCode(postCode);
                    List<PostImage> postImages = PostImageMapper.toPostImageEntities(cachedImages, postCode);
                    postImageRepository.saveAll(postImages);
                }
            }
        }
    }

    private Long extractPostCodeFromKey(String key) {
        return Long.valueOf(key.split(":")[1]);
    }

    private List<PostImageDTO> deserialize(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RedisDeserializationException(RedisExceptionMsg.REDIS_DESERIALIZATION_ERROR);
        }
    }
}