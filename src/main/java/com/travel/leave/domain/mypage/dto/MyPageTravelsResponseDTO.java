package com.travel.leave.domain.mypage.dto;

import java.util.List;

public record MyPageTravelsResponseDTO(
        List<MyPageTravelResponseDTO> myPageTravelResponseDTOs
) {
}