package com.travel.leave.login.oauth.service;

import com.travel.leave.login.oauth.dto.OAuth2AuthenticationDTO;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private final OAuth2AuthenticationDTO oAuth2AuthenticationDTO;
    private final Map<String, Object> attributes;

    private CustomOAuth2User(OAuth2AuthenticationDTO oAuth2AuthenticationDTO, Map<String, Object> attributes) {
        this.oAuth2AuthenticationDTO = oAuth2AuthenticationDTO;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
        collet.add(new SimpleGrantedAuthority(oAuth2AuthenticationDTO.getRole()));
        return collet;
    }

    public Long getCode(){
        return oAuth2AuthenticationDTO.getCode();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return oAuth2AuthenticationDTO.getUsername();
    }

    @Override
    public String getName() {
        return oAuth2AuthenticationDTO.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public static CustomOAuth2User of(OAuth2AuthenticationDTO oAuth2AuthenticationDTO, Map<String, Object> attributes) {
        return new CustomOAuth2User(oAuth2AuthenticationDTO, attributes);
    }
}
