package com.travel.leave.domain.board.controller;

import com.travel.leave.domain.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.subdomain.postcomment.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/comments")
@RequiredArgsConstructor
public class PostCommentController {
    private final PostCommentService postCommentService;

    @PostMapping("/{postCode}")
    public ResponseEntity<PostCommentDTO> addComment(@PathVariable Long postCode,
                                                     @AuthenticationPrincipal Long userCode,
                                                     @RequestBody String content) {
        PostCommentDTO postCommentDTO = postCommentService.addComment(postCode, userCode, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCommentDTO);
    }

    @PutMapping("/{commentCode}")
    public ResponseEntity<PostCommentDTO> updateComment(@PathVariable Long commentCode,
                                                        @AuthenticationPrincipal Long userCode,
                                                        @RequestBody String newContent) {
        PostCommentDTO postCommentDTO = postCommentService.updateComment(commentCode, userCode, newContent);
        return ResponseEntity.status(HttpStatus.OK).body(postCommentDTO);
    }

    @DeleteMapping("/{commentCode}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long commentCode,
                                              @AuthenticationPrincipal Long userCode) {
        Long deletedCommentID = postCommentService.deleteComment(commentCode, userCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedCommentID);
    }
}
