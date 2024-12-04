package com.travel.leave.mypage.repository;

import com.travel.leave.mypage.dto.MyPageTravelsResponseDTO;
import org.springframework.data.domain.Pageable;

public interface MyPageReadQuery {
    MyPageUserResponseDTO findMyPageUserInfo(Long userCode);

    MyPageTravelsResponseDTO findMyPageTravelInfo(Long userCode, Pageable descPageable);
}
