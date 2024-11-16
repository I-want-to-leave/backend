package com.travel.leave.login.basic.service;

import com.travel.leave.login.basic.dto.BasicAuthenticationDTO;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class BasicAuthenticationUserDetails implements UserDetails {
    private final BasicAuthenticationDTO basicAuthenticationDTO;

    public BasicAuthenticationUserDetails(BasicAuthenticationDTO basicAuthenticationDTO) {
        this.basicAuthenticationDTO = basicAuthenticationDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return basicAuthenticationDTO.getRole().toString();
            }
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return basicAuthenticationDTO.getPassword();
    }

    @Override
    public String getUsername() {   //userId를 return
        return basicAuthenticationDTO.getUsername();
    }

    public String getNickname(){
        return basicAuthenticationDTO.getNickname();
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
