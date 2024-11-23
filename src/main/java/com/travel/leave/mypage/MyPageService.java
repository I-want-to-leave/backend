package com.travel.leave.mypage;

import com.travel.leave.login.jwt.service.JWTAuthenticationUserDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
    private final MyPageRepository myPageRepository;

    public MyPageService(MyPageRepository myPageRepository) {
        this.myPageRepository = myPageRepository;
    }

    public MyPageProfileResponseDTO findMyProfile(JWTAuthenticationUserDetails jwtAuthenticationUserDetails) {
        return myPageRepository.findMyPageProfileResponseDTO(jwtAuthenticationUserDetails.getCode());
    }

    public MyPageTravelsResponseDTO findMyTravel(JWTAuthenticationUserDetails jwtAuthenticationUserDetails, Pageable pageable) {
        return myPageRepository.findMyPageTravelsResponseDTO(
                jwtAuthenticationUserDetails.getCode(), pageable.getPageNumber(), pageable.getPageSize());
    }
}
