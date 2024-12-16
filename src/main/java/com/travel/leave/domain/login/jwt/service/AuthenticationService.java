package com.travel.leave.domain.login.jwt.service;

import com.travel.leave.domain.login.jwt.dto.RequestUserDetailsDTO;
import com.travel.leave.subdomain.user.entity.User;
import com.travel.leave.subdomain.user.entity.UserRole;
import com.travel.leave.domain.login.jwt.utility.JWTUtil;
import com.travel.leave.subdomain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public AuthenticationService(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    public String createJWTToken(RequestUserDetailsDTO requestUserDetailsDTO) {
        String username = requestUserDetailsDTO.getProvider() + " " + requestUserDetailsDTO.getProviderId();
        User savedUser = userRepository.findByUsername(username);
        if(savedUser == null) {
            User saveUser = User.builder()
                    .provider(requestUserDetailsDTO.getProvider())
                    .providerId(requestUserDetailsDTO.getProviderId())
                    .username(username)
                    .nickname(requestUserDetailsDTO.getNickname())
                    .email(requestUserDetailsDTO.getEmail())
                    .role(UserRole.ROLE_USER)
                    .build();
            savedUser = userRepository.save(saveUser);
        }

        return jwtUtil.createJWT(
                savedUser.getCode(),
                savedUser.getUsername(),
                savedUser.getNickname(),
                savedUser.getEmail(),
                savedUser.getRole().toString(),
                (long)10000000);
    }
}