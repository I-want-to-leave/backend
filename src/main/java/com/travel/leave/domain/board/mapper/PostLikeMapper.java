package com.travel.leave.domain.board.mapper;

import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.postlike.entity.PostLike;

public class PostLikeMapper {

    public static PostLike toPostLikeEntity(Long postCode, Long userCode) {
        return PostLike.builder()
                .post(Post.builder().postCode(postCode).build())
                .userCode(userCode)
                .build();
    }
}