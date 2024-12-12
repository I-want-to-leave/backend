package com.travel.leave.board.controller;

import com.travel.leave.board.dto.request.create.CreatePostRequestDTO;
import com.travel.leave.board.dto.response.postdetail.PostResponseDTO;
import com.travel.leave.board.service.post.PostService;
import com.travel.leave.board.service.post.TravelSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final TravelSyncService travelSyncService;

    @PostMapping("/{postCode}/share")
    public ResponseEntity<Long> sharePostToTravel(@PathVariable Long postCode,
                                                      @AuthenticationPrincipal Long userCode) {
        Long travelCode = travelSyncService.syncTravelFromPost(postCode, userCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(travelCode);
    }

    @PostMapping("{travelCode}/create")
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequestDTO createPostRequestDTO,
                                           @PathVariable Long travelCode,
                                           @AuthenticationPrincipal Long userCode) {
        postService.createPost(createPostRequestDTO, userCode, travelCode);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{postCode}")
    public ResponseEntity<PostResponseDTO> getPostDetail(@PathVariable Long postCode) {
        PostResponseDTO postResponseDTO = postService.detailPost(postCode);
        return ResponseEntity.ok(postResponseDTO);
    }

    @DeleteMapping("/{postCode}")
    public ResponseEntity<Long> deletePost(@PathVariable Long postCode,
                                           @AuthenticationPrincipal Long userCode) {
        postService.removePost(postCode, userCode);
        return ResponseEntity.noContent().build();
    }
}