package com.travel.leave.board.service.post_image;

import com.travel.leave.board.dto.request.PostImageUpdateRequestDTO;
import com.travel.leave.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.board.entity.PostImage;
import com.travel.leave.board.mapper.PostImageMapper;
import com.travel.leave.board.repository.post_iamge.PostImageOrderRepository;
import com.travel.leave.board.repository.post_iamge.PostImageRepository;
import com.travel.leave.utility.ImageProcessor;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostImageOrderRepository postImageOrderRepository;
    private final CachePostImageManager cachePostImageManager;

    @Transactional
    public List<PostImageDTO> uploadPostImages(Long postCode, Long userCode, List<String> rawImages) {
        Long maxOrder = postImageRepository.findMaxOrderByPostCode(postCode).orElse(0L);
        List<PostImage> postImages = PostImageMapper.toPostImageEntitiesForUpdate(rawImages, postCode, maxOrder);
        postImageRepository.saveAll(postImages);

        List<PostImageDTO> cacheImages = postImages.stream().map(PostImageMapper::toPostImageDTO).collect(Collectors.toList());
        cachePostImageManager.addImages(postCode, cacheImages);
        return cachePostImageManager.getOrLoadImages(postCode);
    }

    @Transactional
    public List<PostImageDTO> deleteImage(Long postCode, Long userCode, String filePath) {
        PostImage imageToDelete = postImageRepository.findFilePath(postCode, filePath)
                .orElseThrow(() -> new EntityNotFoundException("이미지를 찾을 수 없습니다."));
        Long deletedOrder = imageToDelete.getOrder();
        ImageProcessor.deleteImage(filePath);
        postImageRepository.deleteFilePath(postCode, filePath);
        postImageOrderRepository.updateOrderAfterDelete(postCode, deletedOrder);

        cachePostImageManager.removeImageAndReorder(postCode, filePath, deletedOrder);
        return cachePostImageManager.getOrLoadImages(postCode);
    }

    @Transactional
    public List<PostImageDTO> updateImage(Long postCode, Long userCode, PostImageUpdateRequestDTO updateRequestDTO) {
        String newFilePath = ImageProcessor.updateImage(updateRequestDTO.getOldFilePath(), updateRequestDTO.getNewFilePath());
        postImageOrderRepository.updateImageUrl(postCode, updateRequestDTO.getOldFilePath(), newFilePath);

        cachePostImageManager.updateImagePath(postCode, updateRequestDTO.getOldFilePath(), newFilePath);
        return cachePostImageManager.getOrLoadImages(postCode);
    }

    @Transactional
    public List<PostImageDTO> setMainImage(Long postCode, Long userCode, Long targetOrder) {
        postImageOrderRepository.swapZeroImageOrder(postCode, 0L, targetOrder);
        cachePostImageManager.swapMainImage(postCode, 0L, targetOrder);
        return cachePostImageManager.getOrLoadImages(postCode);
    }

    @Transactional(readOnly = true)
    public List<PostImageDTO> getImagesByPostCode(Long postCode) {
        return cachePostImageManager.getOrLoadImages(postCode);
    }
}
