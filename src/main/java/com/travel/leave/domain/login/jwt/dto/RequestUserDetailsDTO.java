package com.travel.leave.domain.login.jwt.dto;

import com.travel.leave.subdomain.user.entity.UserRole;
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
    private UserRole userRole;
}