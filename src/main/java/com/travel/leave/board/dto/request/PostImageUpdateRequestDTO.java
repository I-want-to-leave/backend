package com.travel.leave.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostImageUpdateRequestDTO {
    private String oldFilePath;
    private String newFilePath;
}