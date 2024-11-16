package com.travel.leave.login.basic.dto;

import com.travel.leave.entity.User;
import com.travel.leave.entity.UserRole;
import lombok.Getter;

@Getter
public class BasicAuthenticationDTO {
    private String username;
    private String password;
    private String nickname;
    private UserRole role;


    private BasicAuthenticationDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }

    public static BasicAuthenticationDTO from(User user) {
        return new BasicAuthenticationDTO(user);
    }
}
