package com.travel.leave.domain.board.dto.response.postdetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Schema(description = "게시판 댓글 응답 DTO")
public class PostCommentDTO {
    @Schema(description = "댓글 코드", example = "1")
    private Long code;

    @Schema(description = "댓글 내용", example = "좋은 글 감사합니다!")
    private String content;

    @Schema(description = "댓글 작성 시간", example = "2024-12-19T18:30:00.000Z")
    private Timestamp createdAt;

    @Schema(description = "게시글 코드", example = "101")
    private Long postCode;

    @Schema(description = "작성자 닉네임", example = "송오민다규현")
    private String nickname;

    public PostCommentDTO(Long code, String content, Timestamp createdAt, Long postCode, String nickname) {
        this.code = code;
        this.content = content;
        this.createdAt = createdAt;
        this.postCode = postCode;
        this.nickname = nickname;
    }
}