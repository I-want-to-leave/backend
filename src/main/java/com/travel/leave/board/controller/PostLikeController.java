package com.travel.leave.board.controller;

import com.travel.leave.board.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postCode}/likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<Integer> toggleLike(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode) {
        Integer likeCount = postLikeService.toggleLike(postCode, userCode);
        return ResponseEntity.ok(likeCount);
    } // 좋아요 토글 방식 API -> 추후 서비스 수정 필요할 것
}
