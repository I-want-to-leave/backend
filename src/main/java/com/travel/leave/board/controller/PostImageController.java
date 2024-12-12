package com.travel.leave.board.controller;

import com.travel.leave.board.dto.request.PostImageUpdateRequestDTO;
import com.travel.leave.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.board.service.post_image.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postCode}/images")
public class PostImageController {

    private final PostImageService postImageService;

    @GetMapping
    public ResponseEntity<List<PostImageDTO>> getPostImages(@PathVariable Long postCode) {
        List<PostImageDTO> images = postImageService.getImagesByPostCode(postCode);
        return ResponseEntity.ok(images);
    }

    @PostMapping
    public ResponseEntity<Void> uploadImages(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestBody List<String> imageUrls) {
        postImageService.uploadPostImages(postCode, userCode, imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestParam String filePath) {
        postImageService.deleteImage(postCode, userCode, filePath);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateImage(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestBody PostImageUpdateRequestDTO updateRequestDTO) {
        postImageService.updateImage(postCode, userCode, updateRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/set-main")
    public ResponseEntity<Void> setMainImage(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestParam Long targetOrder) {
        postImageService.setMainImage(postCode, userCode, targetOrder);
        return ResponseEntity.ok().build();
    }
}
