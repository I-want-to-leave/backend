package com.travel.leave.board.mapper;

import com.travel.leave.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.board.entity.Post;
import com.travel.leave.board.entity.PostImage;
import com.travel.leave.utility.ImageProcessor;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PostImageMapper {

    public static List<PostImage> toPostImageEntitiesForCreation(List<String> rawImages, Post post) {
        if (rawImages == null || rawImages.isEmpty()) {
            throw new IllegalArgumentException("이미지 데이터 리스트가 비어있습니다.");
        }

        return rawImages.stream()
                .map(rawImage -> {
                    String filePath = ImageProcessor.saveImage(rawImage);
                    return PostImage.builder()
                            .filePath(filePath)
                            .order(0L)
                            .post(post)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public static List<PostImage> toPostImageEntitiesForUpdate(List<String> rawImages, Long postCode, Long maxOrder) {
        AtomicLong orderCounter = new AtomicLong(maxOrder + 1);

        return rawImages.stream()
                .map(rawImage -> {
                    String filePath = ImageProcessor.saveImage(rawImage);
                    return PostImage.builder()
                            .filePath(filePath)
                            .order(orderCounter.getAndIncrement())
                            .post(Post.builder().postCode(postCode).build())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public static PostImageDTO toPostImageDTO(PostImage postImage) {
        return PostImageDTO.builder()
                .code(postImage.getCode())
                .filePath(postImage.getFilePath())
                .order(postImage.getOrder())
                .build();
    }
}
