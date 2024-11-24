package com.travel.leave.Board.dto;

import com.travel.leave.Board.entity.Post;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreatePostDTO {
    private String title;
    private String content;

    public Post toEntity(Long userCode) {
        return Post.builder()
                .postTitle(this.title)
                .postContent(this.content)
                .userCode(userCode)
                .views(0L)
                .build();
    }
}
