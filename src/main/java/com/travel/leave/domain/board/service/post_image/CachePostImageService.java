package com.travel.leave.domain.board.service.post_image;

import com.travel.leave.domain.board.dto.request.PostImageUpdateRequestDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.domain.board.mapper.PostImageMapper;
import com.travel.leave.domain.board.board_enum.PostImageExceptionMessage;
import com.travel.leave.subdomain.postimage.entity.PostImage;
import com.travel.leave.subdomain.postimage.repository.PostImageRepository;
import com.travel.leave.utility.ImageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CachePostImageService {

    private final CachePostImageManager cachePostImageManager;
    private final PostImageRepository postImageRepository;

    public List<PostImageDTO> getImages(Long postCode) {
        List<PostImageDTO> cachedImages = cachePostImageManager.getImages(postCode);
        if (cachedImages == null) {
            List<PostImage> dbImages = postImageRepository.findImagesByPostCode(postCode);
            cachedImages = PostImageMapper.toPostImageDTOs(dbImages);
            cachePostImageManager.putImages(postCode, cachedImages);
        }
        return cachedImages;
    }

    public List<PostImageDTO> addImages(Long postCode, List<String> rawImages) {
        List<PostImageDTO> cachedImages = getImages(postCode);
        Long maxOrder = cachedImages.stream().mapToLong(PostImageDTO::getOrder).max().orElse(0L);
        List<PostImageDTO> newImageDTOs = PostImageMapper.toPostImageDTOsForUpdate(rawImages, maxOrder);
        cachedImages.addAll(newImageDTOs);
        cachePostImageManager.putImages(postCode, cachedImages);
        return cachedImages;
    }

    public List<PostImageDTO> removeImage(Long postCode, String filePath) {
        List<PostImageDTO> cachedImages = getImages(postCode);
        ImageProcessor.deleteImage(filePath);
        cachedImages.removeIf(image -> image.getFilePath().equals(filePath));
        cachePostImageManager.putImages(postCode, cachedImages);
        return cachedImages;
    }

    public List<PostImageDTO> updateImage(Long postCode, PostImageUpdateRequestDTO updateRequestDTO) {
        List<PostImageDTO> cachedImages = getImages(postCode);
        for (PostImageDTO postImageDTO : cachedImages) {
            if (postImageDTO.getFilePath().equals(updateRequestDTO.getOldFilePath())) {
                postImageDTO.setFilePath(updateRequestDTO.getNewFilePath());
                break;
            }
        }
        cachePostImageManager.putImages(postCode, cachedImages);
        return cachedImages;
    }

    public List<PostImageDTO> swapMainImage(Long postCode, Long targetOrder) {
        List<PostImageDTO> cachedImages = getImages(postCode);

        PostImageDTO zeroImage = cachedImages.stream()
                .filter(img -> img.getOrder() == 0L)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(PostImageExceptionMessage.CACHE_FIND_FAIL_MAIN_IMAGE.getMessage()));

        PostImageDTO targetImage = cachedImages.stream()
                .filter(img -> img.getOrder().equals(targetOrder))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(PostImageExceptionMessage.CACHE_FIND_FAIL_SORTED_IMAGE.getMessage()));

        // 순서 교환
        Long tempOrder = zeroImage.getOrder();
        zeroImage.setOrder(targetImage.getOrder());
        targetImage.setOrder(tempOrder);

        // 캐시 갱신
        cachePostImageManager.putImages(postCode, cachedImages);
        return cachedImages;
    }
}