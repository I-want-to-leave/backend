package com.travel.leave.domain.board.controller;

import com.travel.leave.domain.board.dto.request.CommentRequestDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostCommentDTO;
import com.travel.leave.subdomain.postcomment.service.PostCommentService;
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

@Tag(name = "게시판 댓글 컨트롤러", description = "댓글 추가, 업데이트, 삭제")
@RestController
@RequestMapping("/posts/comments")
@RequiredArgsConstructor
public class PostCommentController {
    private final PostCommentService postCommentService;

    @Operation(summary = "댓글 추가", description = "특정 게시글에 댓글을 추가합니다.")
    @ApiResponse(responseCode = "201", description = "댓글 추가 성공", content = @Content(schema = @Schema(implementation = PostCommentDTO.class)))
    @PostMapping("/{postCode}")
    public ResponseEntity<PostCommentDTO> addComment(@PathVariable Long postCode,
                                                     @AuthenticationPrincipal Long userCode,
                                                     @RequestBody CommentRequestDTO commentRequest) {
        PostCommentDTO postCommentDTO = postCommentService.addComment(postCode, userCode, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCommentDTO);
    }

    @Operation(summary = "댓글 업데이트", description = "특정 댓글을 업데이트합니다.")
    @ApiResponse(responseCode = "201", description = "댓글 업데이트 성공", content = @Content(schema = @Schema(implementation = PostCommentDTO.class)))
    @PutMapping("/{commentCode}")
    public ResponseEntity<PostCommentDTO> updateComment(@PathVariable Long commentCode,
                                                        @AuthenticationPrincipal Long userCode,
                                                        @RequestBody CommentRequestDTO commentRequest) {
        PostCommentDTO postCommentDTO = postCommentService.updateComment(commentCode, userCode, commentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(postCommentDTO);
    }

    @Operation(summary = "댓글 삭제", description = "특정 댓글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 성공")
    @DeleteMapping("/{commentCode}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long commentCode,
                                              @AuthenticationPrincipal Long userCode) {
        Long deletedCommentID = postCommentService.deleteComment(commentCode, userCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedCommentID);
    }
}
