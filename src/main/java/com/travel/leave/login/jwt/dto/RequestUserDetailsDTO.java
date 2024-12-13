package com.travel.leave.login.jwt.dto;

import com.travel.leave.login.entity.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RequestUserDetailsDTO {
    private String provider;
    private String providerId;
    private String nickname;
    private String email;
    private String telNum;
    private UserRole userRole;
}