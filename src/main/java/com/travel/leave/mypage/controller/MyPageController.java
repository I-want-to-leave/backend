package com.travel.leave.mypage.controller;

import com.travel.leave.mypage.service.MyPageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageServiceImpl myPageServiceImpl;

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal Long userCode){
        return ResponseEntity.ok(myPageServiceImpl.getUserInfo(userCode));
    }

    @GetMapping("/travel-info")
    public ResponseEntity<?> getTravelInfo(@AuthenticationPrincipal Long userCode,
                                           @RequestParam int page,
                                           @RequestParam int size){
        return ResponseEntity.ok(myPageServiceImpl.getTravelInfo(userCode, page, size));
    }
}
