package com.travel.leave.domain.board.dto.response;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Schema(description = "게시글 목록 응답 DTO")
public class PostListDTO {

    @Schema(description = "게시글 코드", example = "12345")
    private Long postCode;

    @Schema(description = "게시글 제목", example = "런던 여행 후기")
    private String title;

    @Schema(description = "게시글 부제목", example = "런던에서의 멋진 하루를 공유합니다.")
    private String subtitle;

    @Schema(description = "조회수", example = "10")
    private Long viewCount;

    @Schema(description = "게시글 대표 이미지 경로", example = "https://example.com/image.jpg")
    private String filePath;

    public PostListDTO(Long postCode, String title, String subtitle, Long viewCount, String filePath) {
        this.postCode = postCode;
        this.title = title;
        this.subtitle = subtitle;
        this.viewCount = viewCount;
        this.filePath = filePath;
    }
}
