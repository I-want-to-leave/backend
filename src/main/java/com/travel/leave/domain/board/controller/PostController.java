package com.travel.leave.domain.board.controller;

import com.travel.leave.domain.board.dto.response.postdetail.PostResponseDTO;
import com.travel.leave.subdomain.post.service.PostService;
import com.travel.leave.domain.board.service.post.TravelSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시판 컨트롤러", description = "게시판 API (게시글 생성, 조회, 삭제, 공유)")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final TravelSyncService travelSyncService;

    @Operation(summary = "게시글 생성", description = "여행 기록을 기반으로 게시글을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 생성 성공")
    @PostMapping("{travelCode}/create")
    public ResponseEntity<Long> createPost(@PathVariable Long travelCode, @AuthenticationPrincipal Long userCode) {
        Long postCode = postService.createPost(userCode, travelCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCode);
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공", content = @Content(schema = @Schema(implementation = PostResponseDTO.class)))
    @GetMapping("/{postCode}")
    public ResponseEntity<PostResponseDTO> getPostDetail(@PathVariable Long postCode) {
        PostResponseDTO postResponseDTO = postService.detailPost(postCode);
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDTO);
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "이미지 삭제 성공")
    @DeleteMapping("/{postCode}")
    public ResponseEntity<Long> deletePost(@PathVariable Long postCode,
                                           @AuthenticationPrincipal Long userCode) {
        postService.removePost(postCode, userCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "게시글 공유", description = "게시글을 공유하여 새로운 여행 기록을 생성합니다.")
    @ApiResponse(responseCode = "201", description = "게시글 공유 성공")
    @PostMapping("/{postCode}/share")
    public ResponseEntity<Long> sharePostToTravel(@PathVariable Long postCode,
                                                  @AuthenticationPrincipal Long userCode) {
        Long travelCode = travelSyncService.syncTravelFromPost(postCode, userCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(travelCode);
    }
}