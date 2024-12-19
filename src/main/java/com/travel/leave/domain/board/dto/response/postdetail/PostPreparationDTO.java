package com.travel.leave.domain.board.dto.response.postdetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Schema(description = "게시글 준비물 응답 DTO")
public class PostPreparationDTO {

    @Schema(description = "준비물 이름", example = "여권")
    private String itemName;

    @Schema(description = "준비물 수량", example = "2")
    private Integer quantity;

    public PostPreparationDTO(String itemName, Integer quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
}



