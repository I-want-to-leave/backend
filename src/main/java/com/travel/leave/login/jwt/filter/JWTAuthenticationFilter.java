package com.travel.leave.login.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.leave.login.jwt.dto.AuthenticationResult;
import com.travel.leave.login.jwt.dto.RequestUserDetailsDTO;
import com.travel.leave.login.jwt.dto.ResponseUserDetailsDTO;
import com.travel.leave.login.jwt.service.AuthenticationService;
import com.travel.leave.login.jwt.utility.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 모든 요청에서 JWT를 검사한다.
 */
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final AuthenticationService authenticationService;

    public JWTAuthenticationFilter(JWTUtil jwtUtil, AuthenticationService authenticationService) {
        this.jwtUtil = jwtUtil;
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if(request.getRequestURI().startsWith("/login") && request.getMethod().equals("POST")) {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestUserDetailsDTO requestUserDetailsDTO = objectMapper.readValue(request.getInputStream(), RequestUserDetailsDTO.class);
            jwtToken = authenticationService.createJWTToken(requestUserDetailsDTO);
            response.addHeader("Authorization", "Bearer " + jwtToken);
            response.setStatus(HttpServletResponse.SC_OK);
            jwtToken = "Bearer " + jwtToken;
        }
        if(hasJWTToken(jwtToken) && !isExpired(jwtToken)){
            ResponseUserDetailsDTO responseUserDetailsDTO = ResponseUserDetailsDTO.from(jwtToken);
            Authentication authentication = new AuthenticationResult(responseUserDetailsDTO);
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("SecurityContext at start of request: {}", SecurityContextHolder.getContext());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean hasJWTToken(String jwtToken) {
        return jwtToken != null && jwtToken.startsWith("Bearer ");
    }

    private boolean isExpired(String jwtToken) {
        return jwtUtil.isExpired(jwtToken);
    }
}
