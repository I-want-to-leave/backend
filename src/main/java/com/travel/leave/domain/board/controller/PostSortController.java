package com.travel.leave.domain.board.controller;

import com.travel.leave.domain.board.dto.response.PostListDTO;
import com.travel.leave.domain.board.service.post.PostSortService;
import com.travel.leave.domain.board.board_enum.SortField;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts/sort")
@RequiredArgsConstructor
public class PostSortController {

    private final PostSortService postSortService;

    @GetMapping
    public ResponseEntity<List<PostListDTO>> getSortedPosts(
            @RequestParam SortField SortField,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        List<PostListDTO> postListDTO = postSortService.getSortedPosts(SortField, page, size);
        return ResponseEntity.ok(postListDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostListDTO>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        List<PostListDTO> postListDTO = postSortService.searchPosts(keyword, page, size);
        return ResponseEntity.ok(postListDTO);
    }
}