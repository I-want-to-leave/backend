package com.travel.leave.mypage.service;

import com.travel.leave.mypage.dto.MyPageTravelsResponseDTO;
import com.travel.leave.mypage.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl {
    private final static String ORDER_FIELD = "createdAt";
    private final MyPageRepository myPageRepository;
    public MyPageUserResponseDTO getUserInfo(Long userCode) {
        return myPageRepository.findMyPageUserInfo(userCode);
    }

    public MyPageTravelsResponseDTO getTravelInfo(Long userCode, int page, int size){
        return myPageRepository.findMyPageTravelInfo(userCode, getDescPageable(page, size));
    }

    private Pageable getDescPageable(int page, int size){
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, ORDER_FIELD));
    }
}
