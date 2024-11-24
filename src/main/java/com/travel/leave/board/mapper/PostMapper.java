package com.travel.leave.board.mapper;

import com.travel.leave.board.dto.CreatePostDTO;
import com.travel.leave.board.dto.ResponsePostListDTO;
import com.travel.leave.board.entity.Post;

public class PostMapper {

    public static ResponsePostListDTO fromPostToListDTO(Post post) {
        return ResponsePostListDTO.builder()
                .postCode(post.getPostCode())
                .title(post.getPostTitle())
                .subtitle(extractSubtitle(post.getPostContent()))
                .viewCount(post.getViews())
                .build();
    } // post 엔티티를 게시판 목록 DTO 로 변환

    public static Post ofCreatePostDTO(CreatePostDTO dto, Long userCode) {
        return Post.builder()
                .postTitle(dto.getTitle())
                .postContent(dto.getContent())
                .userCode(userCode)
                .views(0L)
                .build();
    } // 게시물 생성 DTO 를 post 엔티티로 변환

    private static String extractSubtitle(String content) {
        if (content.isEmpty()) {
            return "";
        }
        String firstLine = content.split("\n")[0];
        return firstLine.length() > 10 ? firstLine.substring(0, 10) + "..." : firstLine;
    } // 이건 좀 봐주셈
}