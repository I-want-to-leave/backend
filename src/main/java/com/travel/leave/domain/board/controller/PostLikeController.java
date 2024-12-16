package com.travel.leave.domain.board.controller;

import com.travel.leave.subdomain.postlike.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postCode}/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<Void> toggleLike(@PathVariable Long postCode, @AuthenticationPrincipal Long userCode) {
        postLikeService.toggleLike(postCode, userCode);
        return ResponseEntity.ok().build();
    }
}