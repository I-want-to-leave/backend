package com.travel.leave.login.jwt.service;

import com.travel.leave.login.jwt.dto.JWTAuthenticationDTO;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTAuthenticationUserDetails implements UserDetails {
    private final JWTAuthenticationDTO jwtAuthenticationDTO;

    public JWTAuthenticationUserDetails(JWTAuthenticationDTO jwtAuthenticationDTO) {
        this.jwtAuthenticationDTO = jwtAuthenticationDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return jwtAuthenticationDTO.getUserRole();
            }
        });
        return authorities;
    }

    public Long getCode(){
        return jwtAuthenticationDTO.getCode();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {   //userId를 return
        return jwtAuthenticationDTO.getUsername();
    }

    public String getNickname(){
        return jwtAuthenticationDTO.getNickname();
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
