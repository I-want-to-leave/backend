package com.travel.leave.login.oauth.service;

import com.travel.leave.login.jwt.utility.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    public OAuth2AuthenticationSuccessHandler(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
            log.info("OAuth 로그인 성공했습니다.");

            CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            Long code = customOAuth2User.getCode();
            String username = customOAuth2User.getUsername();
            String nickname = customOAuth2User.getName();
            String role = getRole(authentication);
            String jwtToken = jwtUtil.createJWT(code, username, nickname, role, 60*60*60*10L);
            log.info("Bearer " + jwtToken);
            response.addHeader("Authorization", "Bearer " + jwtToken);
    }

    private String getRole(Authentication authResult){
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        return auth.getAuthority();
    }
}
