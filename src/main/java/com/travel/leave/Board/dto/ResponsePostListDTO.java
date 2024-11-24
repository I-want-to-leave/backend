package com.travel.leave.Board.dto;

import com.travel.leave.Board.entity.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsePostListDTO {
    private Long postCode;
    private String title;
    private String subtitle;
    private Long viewCount;

    public static ResponsePostListDTO fromEntity(Post post) {
        return ResponsePostListDTO.builder()
                .postCode(post.getPostCode())
                .title(post.getPostTitle())
                .subtitle(extractSubtitle(post.getPostContent()))
                .viewCount(post.getViews())
                .build();
    }

    private static String extractSubtitle(String content) {
        if (content.isEmpty()) {
            return "";
        }
        String firstLine = content.split("\n")[0];
        return firstLine.length() > 10 ? firstLine.substring(0, 10) + "..." : firstLine;
    }
}

