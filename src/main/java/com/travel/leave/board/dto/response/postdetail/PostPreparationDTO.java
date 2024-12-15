package com.travel.leave.board.dto.response.postdetail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PostPreparationDTO {
    private String itemName;
    private Integer quantity;

    public PostPreparationDTO(String itemName, Integer quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
}


