package com.travel.leave.domain.mypage.service;

import com.travel.leave.domain.mypage.dto.MyPageCommentInfosResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPageInfoCountsResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPagePostInfosResponseDTO;
import com.travel.leave.domain.mypage.dto.MyPageTravelsResponseDTO;
import com.travel.leave.domain.mypage.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl {
    private final static String ORDER_BY = "createdAt";
    private final MyPageRepository myPageRepository;

    public MyPagePostInfosResponseDTO getPostInfo(Long userCode, int page, int size) {
        return myPageRepository.findMyPagePostInfo(userCode, getDescPageable(page, size));
    }

    public MyPageInfoCountsResponseDTO getInfoCounts(Long userCode) {
        return myPageRepository.findMyPageCountsInfo(userCode);
    }

    public MyPageCommentInfosResponseDTO getCommentInfo(Long userCode, int page, int size) {
        return myPageRepository.findMyPageCommentInfo(userCode, getDescPageable(page, size));
    }

    public MyPageTravelsResponseDTO getTravelInfo(Long userCode, int page, int size) {
        return myPageRepository.findMyPageTravelInfo(userCode, getDescPageable(page, size));
    }

    private Pageable getDescPageable(int page, int size){
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, ORDER_BY));
    }
}
