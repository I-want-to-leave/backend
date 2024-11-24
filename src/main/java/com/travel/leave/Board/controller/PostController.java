package com.travel.leave.Board.controller;

import com.travel.leave.Board.dto.CreatePostDTO;
import com.travel.leave.Board.dto.ResponseDetailPostDTO;
import com.travel.leave.Board.dto.ResponsePostListDTO;
import com.travel.leave.Board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(
            @AuthenticationPrincipal Long userCode,
            @RequestBody CreatePostDTO createPostDTO) {
        postService.createPost(userCode, createPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    } // 게시글 생성

    @GetMapping
    public ResponseEntity<List<ResponsePostListDTO>> getPostList(
            @RequestParam(defaultValue = "0") int page) {
        List<ResponsePostListDTO> response = postService.getPostList(page);
        return ResponseEntity.ok(response);
    } // 게시글 목록 호출

    @GetMapping("/{postCode}")
    public ResponseEntity<ResponseDetailPostDTO> getPost(@PathVariable Long postCode) {
        ResponseDetailPostDTO response = postService.getPost(postCode);
        return ResponseEntity.ok(response);
    } // 게시글 상세내용 호출

    @PutMapping("/{postCode}")
    public ResponseEntity<Void> updatePost(
            @AuthenticationPrincipal Long userCode,
            @PathVariable Long postCode,
            @RequestBody CreatePostDTO createPostDTO) {
        postService.updatePost(postCode, userCode, createPostDTO);
        return ResponseEntity.noContent().build();
    } // 게시글 업데이트

    @DeleteMapping("/{postCode}")
    public ResponseEntity<Void> deletePost(
            @AuthenticationPrincipal Long userCode,
            @PathVariable Long postCode) {
        postService.deletePost(postCode, userCode);
        return ResponseEntity.noContent().build();
    } // 게시글 논리삭제

    @GetMapping("/popular")
    public ResponseEntity<List<ResponsePostListDTO>> getPopularPosts(
            @RequestParam(defaultValue = "0") int page) {
        List<ResponsePostListDTO> popularPosts = postService.getPopularPosts(page);
        return ResponseEntity.ok(popularPosts);
    } // 좋아요 많이 받은 순으로 목록 호출

    @GetMapping("/search")
    public ResponseEntity<List<ResponsePostListDTO>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page) {
        List<ResponsePostListDTO> response = postService.searchPosts(keyword, page);
        return ResponseEntity.ok(response);
    } // 게시글 검색 기능
}