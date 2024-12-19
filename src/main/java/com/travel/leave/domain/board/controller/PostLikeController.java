package com.travel.leave.domain.board.controller;

import com.travel.leave.subdomain.postlike.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시판 좋아요 컨트롤러", description = "게시글 좋아요 API")
@RestController
@RequestMapping("/posts/{postCode}/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "게시글 좋아요 토글", description = "게시글 좋아요를 추가하거나 제거합니다.")
    @ApiResponse(responseCode = "200", description = "좋아요 토글 성공")
    @PostMapping
    public ResponseEntity<Void> toggleLike(@PathVariable Long postCode, @AuthenticationPrincipal Long userCode) {
        postLikeService.toggleLike(postCode, userCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}