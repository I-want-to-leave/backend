package com.travel.leave.mypage;

public record MyPageProfileResponseDTO(
        String nickname,
        String email,
        Integer travelCount,
        Integer postCount,
        Integer postCommentCount
) {
}
