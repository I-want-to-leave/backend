package com.travel.leave.board.controller;

import com.travel.leave.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.board.service.comment.PostCommentService;
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
    public ResponseEntity<PostCommentDTO> addComment(@PathVariable Long postCode,
                                                     @AuthenticationPrincipal Long userCode,
                                                     @RequestBody String content) {
        PostCommentDTO newComment = postCommentService.addComment(postCode, userCode, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }

    @PutMapping("/{commentCode}")
    public ResponseEntity<PostCommentDTO> updateComment(@PathVariable Long postCode,
                                                        @PathVariable Long commentCode,
                                                        @AuthenticationPrincipal Long userCode,
                                                        @RequestBody String newContent) {
        PostCommentDTO updatedComment = postCommentService.updateComment(postCode, commentCode, userCode, newContent);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentCode}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long postCode,
                                              @PathVariable Long commentCode,
                                              @AuthenticationPrincipal Long userCode) {
        Long deletedCommentID = postCommentService.deleteComment(postCode, commentCode, userCode);
        return ResponseEntity.ok(deletedCommentID);
    }
}
