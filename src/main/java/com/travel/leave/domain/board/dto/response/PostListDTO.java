package com.travel.leave.domain.board.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PostListDTO {
    private Long postCode;
    private String title;
    private String subtitle;
    private Long viewCount;
    private String filePath;

    public PostListDTO(Long postCode, String title, String subtitle, Long viewCount, String filePath) {
        this.postCode = postCode;
        this.title = title;
        this.subtitle = subtitle;
        this.viewCount = viewCount;
        this.filePath = filePath;
    }
}