package com.travel.leave.board.dto.response.postdetail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImageDTO {
    private Long code;
    private String filePath;
    private Long order;
}