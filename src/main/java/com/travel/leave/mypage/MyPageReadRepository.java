package com.travel.leave.mypage;

import java.util.Optional;

public interface MyPageReadRepository {

    MyPageTravelsResponseDTO findMyPageTravelsResponseDTO(Long userCode, int page, int size);

    MyPageProfileResponseDTO findMyPageProfileResponseDTO(Long userCode);
}
