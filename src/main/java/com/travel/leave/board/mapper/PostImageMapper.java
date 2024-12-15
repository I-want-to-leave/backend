package com.travel.leave.board.mapper;

import com.travel.leave.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostImage;
import com.travel.leave.utility.ImageProcessor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PostImageMapper {

    public static PostImage toPostImageEntitiesForCreation(String rawImage, Post post) {
        if (rawImage == null || rawImage.isEmpty()) {
            throw new IllegalArgumentException("이미지 데이터 리스트가 비어있습니다.");
        }
        String filePath = ImageProcessor.saveImage(rawImage);
        return PostImage.builder()
                .filePath(filePath)
                .order(0L)
                .post(post)
                .build();
    } // 엔티티 처음 생성 시 쓰이는 메서드

    public static List<PostImageDTO> toPostImageDTOsForUpdate(List<String> rawImages, Long maxOrder) {
        if (rawImages == null || rawImages.isEmpty()) {
            throw new IllegalArgumentException("추가 이미지 데이터가 비어있습니다.");
        }

        AtomicLong orderCounter = new AtomicLong(maxOrder + 1);

        return rawImages.stream()
                .map(rawImage -> PostImageDTO.builder()
                        .filePath(ImageProcessor.saveImage(rawImage)) // 파일 저장
                        .order(orderCounter.getAndIncrement()) // 새로운 순서
                        .build())
                .toList();
    } // 이미지 업로드 시, 캐시 처리 매퍼 로직

    public static List<PostImage> toPostImageEntities(List<PostImageDTO> cachedImages, Long postCode) {
        if (cachedImages == null || cachedImages.isEmpty()) {
            throw new IllegalArgumentException("캐싱된 이미지 데이터가 비어있습니다.");
        }

        return cachedImages.stream()
                .map(cachedImage -> PostImage.builder()
                        .code(cachedImage.getCode())
                        .filePath(cachedImage.getFilePath())
                        .order(cachedImage.getOrder())
                        .post(Post.builder().postCode(postCode).build())
                        .build())
                .toList();
    }

    public static List<PostImageDTO> toPostImageDTOs(List<PostImage> postImages) {
        if (postImages == null || postImages.isEmpty()) {
            return Collections.emptyList();
        }

        return postImages.stream()
                .map(PostImageMapper::toPostImageDTO)
                .toList();
    }

    public static PostImageDTO toPostImageDTO(PostImage postImage) {
        return PostImageDTO.builder()
                .code(postImage.getCode())
                .filePath(postImage.getFilePath())
                .order(postImage.getOrder())
                .build();
    }
}
