package com.travel.leave.board.mapper;

import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostComment;
import org.springframework.stereotype.Component;

@Component
public class PostCommentMapper {

    public PostComment createPostCommentEntity(Post post, Long userCode, String content, String nickname) {
        return PostComment.builder()
                .post(post)
                .userCode(userCode)
                .content(content)
                .nickname(nickname)
                .build();
    }
}
