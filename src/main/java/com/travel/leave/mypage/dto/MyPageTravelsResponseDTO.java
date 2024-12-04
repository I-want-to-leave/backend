package com.travel.leave.mypage.dto;

import java.util.List;

public record MyPageTravelsResponseDTO(
        String nickname,
        List<MyPageTravelResponseDTO> myPageTravelResponseDTOs
) {
}
