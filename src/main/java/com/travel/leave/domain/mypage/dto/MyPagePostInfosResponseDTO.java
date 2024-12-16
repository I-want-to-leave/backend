package com.travel.leave.domain.mypage.dto;

import java.util.List;

public record MyPagePostInfosResponseDTO(
        List<MyPagePostInfoResponseDTO> myPagePostInfoResponseDTOs
) {
}