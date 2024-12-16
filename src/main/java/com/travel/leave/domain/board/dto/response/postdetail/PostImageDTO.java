package com.travel.leave.domain.board.dto.response.postdetail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PostImageDTO {
    private Long code;
    private String filePath;
    private Long order;

    public PostImageDTO(Long code, String filePath, Long order) {
        this.code = code;
        this.filePath = filePath;
        this.order = order;
    }
}
