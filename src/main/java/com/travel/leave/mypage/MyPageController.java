package com.travel.leave.mypage;

import com.travel.leave.login.jwt.service.JWTAuthenticationUserDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/my-page")
public class MyPageController {
    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> findMyProfile(@AuthenticationPrincipal JWTAuthenticationUserDetails jwtAuthenticationUserDetails){
        return ResponseEntity.ok().body(myPageService.findMyProfile(jwtAuthenticationUserDetails));
    }

    @GetMapping("/travels")
    public ResponseEntity<?> findMyTravels(
            @AuthenticationPrincipal JWTAuthenticationUserDetails jwtAuthenticationUserDetails,
            Pageable pageable){
        return ResponseEntity.ok().body(myPageService.findMyTravel(jwtAuthenticationUserDetails, pageable));
    }
}
