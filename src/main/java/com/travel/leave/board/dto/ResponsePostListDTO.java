package com.travel.leave.board.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ResponsePostListDTO {
    private Long postCode;
    private String title;
    private String subtitle;
    private Long viewCount;

    public ResponsePostListDTO(Long postCode, String title, String subtitle, Long viewCount) {
        this.postCode = postCode;
        this.title = title;
        this.subtitle = subtitle;
        this.viewCount = viewCount;
    }
}
