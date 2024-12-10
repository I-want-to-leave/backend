package com.travel.leave.mypage.dto;

import java.util.List;

public record MyPageCommentInfosResponseDTO(
        List<MyPageCommentInfoResponseDTO> myPageCommentInfoResponseDTOs
) {
}
