package com.travel.leave.Board.controller;

import com.travel.leave.Board.dto.PostCommentDTO;
import com.travel.leave.Board.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postCode}/comments")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping
    public ResponseEntity<PostCommentDTO> addComment(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestBody String content) {
        PostCommentDTO comment = postCommentService.addComment(postCode, userCode, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/{commentCode}")
    public ResponseEntity<PostCommentDTO> updateComment(
            @PathVariable Long postCode,
            @PathVariable Long commentCode,
            @AuthenticationPrincipal Long userCode,
            @RequestBody String newContent) {
        PostCommentDTO updatedComment = postCommentService.updateComment(postCode, commentCode, userCode, newContent);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentCode}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postCode,
            @PathVariable Long commentCode,
            @AuthenticationPrincipal Long userCode) {
        postCommentService.deleteComment(postCode, commentCode, userCode);
        return ResponseEntity.noContent().build();
    }
}

