package com.travel.leave.login.jwt.dto;


import com.travel.leave.entity.UserRole;
import com.travel.leave.login.jwt.utility.JWTUtil;
import lombok.Getter;

@Getter
public class JWTAuthenticationDTO {
    private String username;
    private String nickname;
    private UserRole userRole;

    private JWTAuthenticationDTO(String jwtToken) {
        this.username = JWTUtil.getUsername(jwtToken);
        this.nickname = JWTUtil.getNickname(jwtToken);
        this.userRole = UserRole.valueOf(JWTUtil.getRole(jwtToken));
    }

    public String getUserRole() {
        return userRole.toString();
    }

    public static JWTAuthenticationDTO from(String jwtToken) {
        return new JWTAuthenticationDTO(jwtToken);
    }
}
