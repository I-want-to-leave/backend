package com.travel.leave.login.jwt.dto;

import com.travel.leave.login.entity.UserRole;
import com.travel.leave.login.jwt.utility.JWTUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserDetailsDTO {
    private Long code;
    private String username;
    private String nickname;
    private String email;
    private UserRole userRole;

    public ResponseUserDetailsDTO(String jwtToken) {
        this.code = JWTUtil.getCode(jwtToken);
        this.username = JWTUtil.getUsername(jwtToken);
        this.nickname = JWTUtil.getNickname(jwtToken);
        this.email = JWTUtil.getEmail(jwtToken);
        this.userRole = UserRole.ROLE_USER;
    }

    public static ResponseUserDetailsDTO from(String jwtToken) {
        return new ResponseUserDetailsDTO(jwtToken);
    }
}