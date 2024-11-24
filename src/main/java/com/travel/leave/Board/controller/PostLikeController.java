package com.travel.leave.Board.controller;

import com.travel.leave.Board.service.PostLikeService;
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
    }
}
