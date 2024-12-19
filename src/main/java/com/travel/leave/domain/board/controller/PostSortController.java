package com.travel.leave.domain.board.controller;

import com.travel.leave.domain.board.dto.response.PostListDTO;
import com.travel.leave.domain.board.service.post.PostSortService;
import com.travel.leave.domain.board.service.post.SortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "게시판 정렬 컨트롤러", description = "게시글 정렬 및 검색 API")
@RestController
@RequestMapping("/posts/sort")
@RequiredArgsConstructor
public class PostSortController {

    private final PostSortService postSortService;

    @Operation(summary = "게시글 정렬", description = "정렬 기준에 따라 게시글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 정렬 조회 성공", content = @Content(schema = @Schema(implementation = PostListDTO.class)))
    @GetMapping
    public ResponseEntity<List<PostListDTO>> getSortedPosts(
            @Parameter(description = "정렬 기준", schema = @Schema(implementation = SortField.class))
            @RequestParam SortField SortField,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        List<PostListDTO> postListDTO = postSortService.getSortedPosts(SortField, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(postListDTO);
    }

    @Operation(summary = "게시글 검색", description = "키워드를 기반으로 게시글을 검색합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 검색 성공",
            content = @Content(schema = @Schema(implementation = PostListDTO.class)))
    @GetMapping("/search")
    public ResponseEntity<List<PostListDTO>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        List<PostListDTO> postListDTO = postSortService.searchPosts(keyword, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(postListDTO);
    }
}
