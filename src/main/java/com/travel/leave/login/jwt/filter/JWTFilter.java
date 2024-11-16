package com.travel.leave.login.jwt.filter;


import com.travel.leave.login.jwt.dto.JWTAuthenticationDTO;
import com.travel.leave.login.jwt.service.JWTAuthenticationUserDetails;
import com.travel.leave.login.jwt.utility.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 모든 요청에서 JWT를 검사한다.
 */
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //jwt가 존재하면 ContextHolder(threadLocal)에 저장하여 하나의 요청(하나의 쓰레드)에서 jwt의 정보를 이용할 수 있게 한다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(request.getRequestURI());
        String jwtToken = request.getHeader("Authorization");
        if(!hasAuthorizationInHeader(jwtToken) || jwtUtil.isExpired(jwtToken)) {   //토큰이 없을 시 == 로그인 x 시 CustomLoginFilter 에서 처리
            log.info("jwt가 만료됐거나, 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }
        //토큰이 있고, 만료되지 않았으면(유효하면)
        JWTAuthenticationDTO jwtAuthenticationDTO = JWTAuthenticationDTO.from(jwtToken);
        JWTAuthenticationUserDetails jwtAuthenticationUserDetails = new JWTAuthenticationUserDetails(jwtAuthenticationDTO);
        Authentication authToken = new UsernamePasswordAuthenticationToken(jwtAuthenticationUserDetails, "", jwtAuthenticationUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationInHeader(String jwtToken) {
        if(jwtToken == null){
            log.info("헤더에 토큰 자체가 없음");
            return false;
        }
        if(!jwtToken.startsWith("Bearer ")){
            log.info("토큰에 Bearer를 추가해야함");
            return false;
        }
        return true;
    }
}
