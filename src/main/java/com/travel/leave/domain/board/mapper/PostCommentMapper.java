package com.travel.leave.domain.board.mapper;

import com.travel.leave.subdomain.post.entity.Post;
import com.travel.leave.subdomain.postcomment.entity.PostComment;
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
