package com.travel.leave.domain.board.service.post_image;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisImageField {
    REDIS_POST_IMAGE_KEY("postImageCache:"),
    REDIS_POST_IMAGE_KEYS("postImageCache:*");

    private final String value;
}