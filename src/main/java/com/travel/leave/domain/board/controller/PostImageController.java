package com.travel.leave.domain.board.controller;

import com.travel.leave.domain.board.dto.request.PostImageUpdateRequestDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.subdomain.postimage.service.PostImageService;
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

import java.util.List;

@Tag(name = "게시판 이미지 컨트롤러", description = "게시글 이미지 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postCode}/images")
public class PostImageController {

    private final PostImageService postImageService;

    @Operation(summary = "게시글 이미지 조회", description = "게시글에 포함된 모든 이미지를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "이미지 조회 성공", content = @Content(schema = @Schema(implementation = PostImageDTO.class)))
    @GetMapping
    public ResponseEntity<List<PostImageDTO>> getPostImages(@PathVariable Long postCode) {
        List<PostImageDTO> postImageDTOs = postImageService.getImagesByPostCode(postCode);
        return ResponseEntity.status(HttpStatus.OK).body(postImageDTOs);
    }

    @Operation(summary = "이미지 업로드", description = "게시글에 이미지를 업로드합니다.")
    @ApiResponse(responseCode = "201", description = "이미지 업로드 성공", content = @Content(schema = @Schema(implementation = PostImageDTO.class)))
    @PostMapping
    public ResponseEntity<List<PostImageDTO>> uploadImages(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestBody List<String> imageUrls) {
        List<PostImageDTO> postImageDTOs = postImageService.uploadPostImages(postCode, userCode, imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).body(postImageDTOs);
    }

    @Operation(summary = "이미지 삭제", description = "게시글에서 이미지를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "이미지 삭제 성공", content = @Content(schema = @Schema(implementation = PostImageDTO.class)))
    @DeleteMapping
    public ResponseEntity<List<PostImageDTO>> deleteImage(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestParam String filePath) {
        List<PostImageDTO> postImageDTOs = postImageService.deleteImage(postCode, userCode, filePath);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postImageDTOs);
    }

    @Operation(summary = "이미지 업데이트", description = "게시글의 이미지를 업데이트합니다.")
    @ApiResponse(responseCode = "200", description = "이미지 업데이트 성공", content = @Content(schema = @Schema(implementation = PostImageDTO.class)))
    @PutMapping("/update")
    public ResponseEntity<List<PostImageDTO>> updateImage(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestBody PostImageUpdateRequestDTO updateRequestDTO) {
        List<PostImageDTO> postImageDTOs = postImageService.updateImage(postCode, userCode, updateRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(postImageDTOs);
    }

    @Operation(summary = "대표 이미지 설정", description = "게시글의 대표 이미지를 설정합니다.")
    @ApiResponse(responseCode = "200", description = "대표 이미지 설정 성공", content = @Content(schema = @Schema(implementation = PostImageDTO.class)))
    @PatchMapping("/set-main")
    public ResponseEntity<List<PostImageDTO>> setMainImage(
            @PathVariable Long postCode,
            @AuthenticationPrincipal Long userCode,
            @RequestParam Long targetOrder) {
        List<PostImageDTO> postImageDTOs = postImageService.setMainImage(postCode, userCode, targetOrder);
        return ResponseEntity.status(HttpStatus.OK).body(postImageDTOs);
    }
}