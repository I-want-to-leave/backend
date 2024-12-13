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
    private final CachePostImageService cachePostImageService;

    @Transactional @SuppressWarnings("unused")
    public List<PostImageDTO> uploadPostImages(Long postCode, Long userCode, List<String> rawImages) {
        Long maxOrder = postImageRepository.findMaxOrderByPostCode(postCode).orElse(0L);
        List<PostImage> postImages = PostImageMapper.toPostImageEntitiesForUpdate(rawImages, postCode, maxOrder);
        postImageRepository.saveAll(postImages);
        List<PostImageDTO> cacheImages = postImages.stream().map(PostImageMapper::toPostImageDTO).collect(Collectors.toList());

        if (cachePostImageService.getImages(postCode) == null) {
            List<PostImageDTO> images = loadImagesFromDB(postCode);
            cachePostImageService.putImages(postCode, images);
        } else {
            cachePostImageService.addImages(postCode, cacheImages);
        }
        return cachePostImageService.getImages(postCode);
    } // userCode 는 AOP 에서 사용

    @Transactional @SuppressWarnings("unused")
    public List<PostImageDTO> deleteImage(Long postCode, Long userCode, String filePath) {
        PostImage imageToDelete = postImageRepository.findFilePath(postCode, filePath)
                .orElseThrow(() -> new EntityNotFoundException("이미지를 찾을 수 없습니다."));

        Long deletedOrder = imageToDelete.getOrder();
        ImageProcessor.deleteImage(filePath); // 실제 파일 삭제
        postImageRepository.deleteFilePath(postCode, filePath); // 파일 경로 삭제
        postImageOrderRepository.updateOrderAfterDelete(postCode, deletedOrder); // 파일 순서 조정

        if (cachePostImageService.getImages(postCode) == null) {
            cachePostImageService.putImages(postCode, loadImagesFromDB(postCode));
        } else {
            cachePostImageService.removeImageAndReorder(postCode, filePath, deletedOrder);
        }
        return cachePostImageService.getImages(postCode);
    } // userCode 는 AOP 에서 사용

    @Transactional @SuppressWarnings("unused")
    public List<PostImageDTO> updateImage(Long postCode, Long userCode, PostImageUpdateRequestDTO updateRequestDTO) {
        String newFilePath = ImageProcessor.updateImage(updateRequestDTO.getOldFilePath(), updateRequestDTO.getNewFilePath());
        postImageOrderRepository.updateImageUrl(postCode, updateRequestDTO.getOldFilePath(), newFilePath);

        if (cachePostImageService.getImages(postCode) == null) {
            cachePostImageService.putImages(postCode, loadImagesFromDB(postCode));
        } else {
            cachePostImageService.updateImagePath(postCode, updateRequestDTO.getOldFilePath(), newFilePath);
        }
        return cachePostImageService.getImages(postCode);
    } // userCode 는 AOP 에서 사용

    @Transactional @SuppressWarnings("unused")
    public List<PostImageDTO> setMainImage(Long postCode, Long userCode, Long targetOrder) {
        postImageOrderRepository.swapZeroImageOrder(postCode, 0L, targetOrder);

        if (cachePostImageService.getImages(postCode) == null) {
            cachePostImageService.putImages(postCode, loadImagesFromDB(postCode));
        } else {
            cachePostImageService.swapMainImage(postCode, 0L, targetOrder);
        }

        return cachePostImageService.getImages(postCode);
    } // userCode 는 AOP 에서 사용

    @Transactional(readOnly = true)
    public List<PostImageDTO> getImagesByPostCode(Long postCode) {
        List<PostImageDTO> cachedList = cachePostImageService.getImages(postCode);
        if (cachedList == null) {
            cachedList = loadImagesFromDB(postCode);
            cachePostImageService.putImages(postCode, cachedList);
        }
        return cachedList;
    } // userCode 는 AOP 에서 사용

    private List<PostImageDTO> loadImagesFromDB(Long postCode) {
        return postImageRepository.findImagesByPostCode(postCode).stream()
                .map(PostImageMapper::toPostImageDTO)
                .collect(Collectors.toList());
    }
}
