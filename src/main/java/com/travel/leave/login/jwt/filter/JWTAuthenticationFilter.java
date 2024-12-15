package com.travel.leave.login.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.leave.login.jwt.dto.AuthenticationResult;
import com.travel.leave.login.jwt.dto.RequestUserDetailsDTO;
import com.travel.leave.login.jwt.dto.ResponseUserDetailsDTO;
import com.travel.leave.login.jwt.service.AuthenticationService;
import com.travel.leave.login.jwt.utility.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        try {
            String jwtToken = request.getHeader("Authorization");

            if (request.getRequestURI().startsWith("/login") && request.getMethod().equals("POST")) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    RequestUserDetailsDTO requestUserDetailsDTO = objectMapper.readValue(request.getInputStream(), RequestUserDetailsDTO.class);
                    jwtToken = authenticationService.createJWTToken(requestUserDetailsDTO);
                    response.addHeader("Authorization", "Bearer " + jwtToken);
                    response.setStatus(HttpServletResponse.SC_OK);
                    log.info("Bearer " + jwtToken);
                    return;
                } catch (IOException e) {
                    log.error("Failed to parse request body: {}", e.getMessage());
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request body");
                    return;
                }
            }

            if (hasJWTToken(jwtToken)) {
                if (isExpired(jwtToken)) {
                    log.warn("JWT is expired");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT is expired");
                    return;
                }

                String role = JWTUtil.getRole(jwtToken);
                log.info("Parsed role from JWT: {}", role);

                ResponseUserDetailsDTO responseUserDetailsDTO = ResponseUserDetailsDTO.from(jwtToken);
                Authentication authentication = new AuthenticationResult(responseUserDetailsDTO) {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return List.of(new SimpleGrantedAuthority(role));
                    }
                };
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication set for user: {}", responseUserDetailsDTO.getUsername());
            } else {
                log.warn("Missing Authorization header");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Authorization header");
                return;
            }

            log.info("Before filterChain.doFilter: {}", SecurityContextHolder.getContext().getAuthentication());
            filterChain.doFilter(request, response);
            log.info("After filterChain.doFilter: {}", SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            log.error("Error during filtering: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    private boolean hasJWTToken(String jwtToken) {
        return jwtToken != null;
    }

    private boolean isExpired(String jwtToken) {
        return jwtUtil.isExpired(jwtToken);
    }
}