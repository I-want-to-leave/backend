package com.travel.leave.mypage.repository;

import com.travel.leave.mypage.dto.MyPageCommentInfosResponseDTO;
import com.travel.leave.mypage.dto.MyPageInfoCountsResponseDTO;
import com.travel.leave.mypage.dto.MyPagePostInfoResponseDTO;
import com.travel.leave.mypage.dto.MyPagePostInfosResponseDTO;
import com.travel.leave.mypage.dto.MyPageTravelsResponseDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface MyPageReadQuery {
    MyPagePostInfosResponseDTO findMyPagePostInfo(Long userCode, Pageable pageable);

    MyPageCommentInfosResponseDTO findMyPageCommentInfo(Long userCode, Pageable pageable);

    MyPageInfoCountsResponseDTO findMyPageCountsInfo(Long userCode);

    MyPageTravelsResponseDTO findMyPageTravelInfo(Long userCode, Pageable pageable);
}
