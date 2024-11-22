package com.travel.leave.login.basic.filter;

import com.travel.leave.login.basic.service.BasicAuthenticationUserDetails;
import com.travel.leave.login.jwt.utility.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public CustomLoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("로그인 성공했습니다.");

        BasicAuthenticationUserDetails basicAuthenticationUserDetails = (BasicAuthenticationUserDetails) authentication.getPrincipal();

        Long code = basicAuthenticationUserDetails.getCode();
        String username = basicAuthenticationUserDetails.getUsername();
        String nickname = basicAuthenticationUserDetails.getNickname();
        String role = getRole(authentication);
        String jwtToken = jwtUtil.createJWT(code, username, nickname, role, 60*60*60*10L);

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("로그인 실패");
        response.setStatus(401);
    }

    private String getRole(Authentication authResult){
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        return auth.getAuthority();
    }
}
