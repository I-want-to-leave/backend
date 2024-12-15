package com.travel.leave.login.jwt.dto;

import com.travel.leave.entity.UserRole;
import jakarta.servlet.http.HttpServletRequest;
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