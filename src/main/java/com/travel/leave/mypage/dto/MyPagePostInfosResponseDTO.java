package com.travel.leave.mypage.dto;

import java.util.List;

public record MyPagePostInfosResponseDTO(
        List<MyPagePostInfoResponseDTO> myPagePostInfoResponseDTOs
) {
}