package com.travel.leave.domain.board.controller;

import com.travel.leave.domain.board.dto.request.PostImageUpdateRequestDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.subdomain.postimage.service.PostImageService;
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
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @PostMapping
    public ResponseEntity<List<PostImageDTO>> uploadImages(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestBody List<String> imageUrls) {
        List<PostImageDTO> images = postImageService.uploadPostImages(postCode, userCode, imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).body(images);
    }

    @DeleteMapping
    public ResponseEntity<List<PostImageDTO>> deleteImage(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestParam String filePath) {
        List<PostImageDTO> images = postImageService.deleteImage(postCode, userCode, filePath);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(images);
    }

    @PutMapping("/update")
    public ResponseEntity<List<PostImageDTO>> updateImage(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestBody PostImageUpdateRequestDTO updateRequestDTO) {
        List<PostImageDTO> images = postImageService.updateImage(postCode, userCode, updateRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @PatchMapping("/set-main")
    public ResponseEntity<List<PostImageDTO>> setMainImage(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestParam Long targetOrder) {
        List<PostImageDTO> images = postImageService.setMainImage(postCode, userCode, targetOrder);
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }
}