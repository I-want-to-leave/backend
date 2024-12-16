package com.travel.leave.domain.login.config;


import com.travel.leave.domain.login.jwt.filter.JWTAuthenticationFilter;
import com.travel.leave.domain.login.jwt.service.AuthenticationService;
import com.travel.leave.domain.login.jwt.utility.JWTUtil;
import com.travel.leave.subdomain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JWTUtil jwtUtil;

    public SecurityConfig(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserRepository userRepository,
                                           AuthenticationService authenticationService) throws Exception {
        http        //csrf, formLogin, httpBasic 모두 안 쓴다 선언
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);


        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/favicon.ico", "/join", "/oauth2/authorization/**", "/travel-socket", "/ws/**").permitAll()
                        .anyRequest().authenticated());


        http        //httpSession안 쓴다 선언 => stateless
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http        //cors 설정
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowCredentials(true);
                        configuration.addAllowedOrigin("*");
                        configuration.addAllowedHeader("*");
                        configuration.addAllowedMethod("*");
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                        return configuration;
                    }
                }));

        http.addFilterBefore(new JWTAuthenticationFilter(jwtUtil, authenticationService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}