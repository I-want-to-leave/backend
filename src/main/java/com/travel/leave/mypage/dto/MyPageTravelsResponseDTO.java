package com.travel.leave.mypage.dto;

import java.util.List;

public record MyPageTravelsResponseDTO(
        List<MyPageTravelResponseDTO> myPageTravelResponseDTOs
) {
}