package com.travel.leave.login.oauth.dto;

import com.travel.leave.entity.User;
import com.travel.leave.entity.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class OAuth2AuthenticationDTO {
    private String username;
    private String nickname;
    private UserRole userRole;

    private OAuth2AuthenticationDTO(User user, String username) {
        this.username = username;
        this.nickname = user.getNickname();
        this.userRole = user.getRole();
    }

    public static OAuth2AuthenticationDTO of(User databaseExistUser, String oAuthCode) {
        return new OAuth2AuthenticationDTO(databaseExistUser, oAuthCode);
    }

    public String getRole() {
        return userRole.toString();
    }
}
