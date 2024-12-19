package com.travel.leave.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {

    @Schema(description = "댓글 내용", example = "좋은 글 감사합니다!")
    private String content;
}
