package com.travel.leave.domain.board.controller;

import com.travel.leave.domain.board.dto.response.postdetail.PostResponseDTO;
import com.travel.leave.subdomain.post.service.PostService;
import com.travel.leave.domain.board.service.post.TravelSyncService;
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

    @PostMapping("{travelCode}/create")
    public ResponseEntity<Long> createPost(@PathVariable Long travelCode, @AuthenticationPrincipal Long userCode) {
        Long postCode = postService.createPost(userCode, travelCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCode);
    }

    @GetMapping("/{postCode}")
    public ResponseEntity<PostResponseDTO> getPostDetail(@PathVariable Long postCode) {
        PostResponseDTO postResponseDTO = postService.detailPost(postCode);
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDTO);
    }

    @DeleteMapping("/{postCode}")
    public ResponseEntity<Long> deletePost(@PathVariable Long postCode,
                                           @AuthenticationPrincipal Long userCode) {
        postService.removePost(postCode, userCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{postCode}/share")
    public ResponseEntity<Long> sharePostToTravel(@PathVariable Long postCode,
                                                  @AuthenticationPrincipal Long userCode) {
        Long travelCode = travelSyncService.syncTravelFromPost(postCode, userCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(travelCode);
    }
}