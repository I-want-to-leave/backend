package com.travel.leave.domain.board.dto.response.postdetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Schema(description = "게시글 이미지 응답 DTO")
public class PostImageDTO {
    @Schema(description = "이미지 코드", example = "101")
    private Long code;

    @Schema(description = "이미지 경로", example = "https://example.com/image.jpg")
    private String filePath;

    @Schema(description = "이미지 순서", example = "1")
    private Long order;

    public PostImageDTO(Long code, String filePath, Long order) {
        this.code = code;
        this.filePath = filePath;
        this.order = order;
    }
}
