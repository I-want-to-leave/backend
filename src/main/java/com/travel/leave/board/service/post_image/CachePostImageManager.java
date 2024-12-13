package com.travel.leave.board.service.post_image;

import com.travel.leave.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.board.mapper.PostImageMapper;
import com.travel.leave.board.repository.post_iamge.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CachePostImageManager {

    private final CachePostImageService cachePostImageService;
    private final PostImageRepository postImageRepository;

    public List<PostImageDTO> getOrLoadImages(Long postCode) {
        List<PostImageDTO> cachedList = cachePostImageService.getImages(postCode);
        if (cachedList == null) {
            cachedList = loadImagesFromDB(postCode);
            cachePostImageService.putImages(postCode, cachedList);
        }
        return cachedList;
    }

    private List<PostImageDTO> loadImagesFromDB(Long postCode) {
        return postImageRepository.findImagesByPostCode(postCode).stream()
                .map(PostImageMapper::toPostImageDTO)
                .collect(Collectors.toList());
    }

    public void addImages(Long postCode, List<PostImageDTO> cacheImages) {
        if (cachePostImageService.getImages(postCode) == null) {
            List<PostImageDTO> images = loadImagesFromDB(postCode);
            cachePostImageService.putImages(postCode, images);
        } else {
            cachePostImageService.addImages(postCode, cacheImages);
        }
    }

    public void removeImageAndReorder(Long postCode, String filePath, Long deletedOrder) {
        if (cachePostImageService.getImages(postCode) == null) {
            cachePostImageService.putImages(postCode, loadImagesFromDB(postCode));
        } else {
            cachePostImageService.removeImageAndReorder(postCode, filePath, deletedOrder);
        }
    }

    public void updateImagePath(Long postCode, String oldFilePath, String newFilePath) {
        if (cachePostImageService.getImages(postCode) == null) {
            cachePostImageService.putImages(postCode, loadImagesFromDB(postCode));
        } else {
            cachePostImageService.updateImagePath(postCode, oldFilePath, newFilePath);
        }
    }

    public void swapMainImage(Long postCode, Long currentOrder, Long newOrder) {
        if (cachePostImageService.getImages(postCode) == null) {
            cachePostImageService.putImages(postCode, loadImagesFromDB(postCode));
        } else {
            cachePostImageService.swapMainImage(postCode, currentOrder, newOrder);
        }
    }
}