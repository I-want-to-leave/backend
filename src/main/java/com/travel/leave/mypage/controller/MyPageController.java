package com.travel.leave.mypage.controller;

import com.travel.leave.mypage.service.MyPageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageServiceImpl myPageServiceImpl;

    @GetMapping("/user-info")
    public ResponseEntity<?> getInfosCount(@AuthenticationPrincipal Long userCode){
        return ResponseEntity.ok(myPageServiceImpl.getInfoCounts(userCode));
    }

    @GetMapping("/comment-info")
    public ResponseEntity<?> getCommentInfo(@AuthenticationPrincipal Long userCode,
                                            @RequestParam int page,
                                            @RequestParam int size){
        return ResponseEntity.ok(myPageServiceImpl.getCommentInfo(userCode, page, size));
    }

    @GetMapping("/post-info")
    public ResponseEntity<?> getPostInfo(@AuthenticationPrincipal Long userCode,
                                         @RequestParam int page,
                                         @RequestParam int size){
        return ResponseEntity.ok(myPageServiceImpl.getPostInfo(userCode, page, size));
    }

    @GetMapping("/travel-info")
    public ResponseEntity<?> getTravelInfo(@AuthenticationPrincipal Long userCode,
                                           @RequestParam int page,
                                           @RequestParam int size){
        return ResponseEntity.ok(myPageServiceImpl.getTravelInfo(userCode, page, size));
    }
}