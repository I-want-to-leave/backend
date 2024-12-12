package com.travel.leave.board.controller;

import com.travel.leave.board.dto.response.PostListDTO;
import com.travel.leave.board.service.post.PostSortService;
import com.travel.leave.board.service.enums.SortField;
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
            @RequestParam SortField sortField,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        List<PostListDTO> postListDTO = postSortService.getSortedPosts(sortField, page, size);
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