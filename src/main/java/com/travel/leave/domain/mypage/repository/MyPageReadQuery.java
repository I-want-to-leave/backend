package com.travel.leave.domain.mypage.repository;

import com.travel.leave.domain.mypage.dto.MyPageCommentInfosResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPageInfoCountsResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPagePostInfosResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPageTravelsResponseDTO;
import org.springframework.data.domain.Pageable;

public interface MyPageReadQuery {
    MyPagePostInfosResponseDTO findMyPagePostInfo(Long userCode, Pageable pageable);

    MyPageCommentInfosResponseDTO findMyPageCommentInfo(Long userCode, Pageable pageable);

    MyPageInfoCountsResponseDTO findMyPageCountsInfo(Long userCode);

    MyPageTravelsResponseDTO findMyPageTravelInfo(Long userCode, Pageable pageable);
}
