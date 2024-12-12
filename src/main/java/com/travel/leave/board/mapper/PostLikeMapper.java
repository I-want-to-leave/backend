package com.travel.leave.board.mapper;

import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostLike;

public class PostLikeMapper {

    public static PostLike toPostLikeEntity(Long postCode, Long userCode) {
        return PostLike.builder()
                .post(Post.builder().postCode(postCode).build())
                .userCode(userCode)
                .build();
    }
}