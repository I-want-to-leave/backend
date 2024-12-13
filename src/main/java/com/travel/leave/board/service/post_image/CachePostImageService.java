package com.travel.leave.board.service.post_image;

import com.travel.leave.board.dto.response.postdetail.PostImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CachePostImageService {

    private final ConcurrentHashMap<Long, List<PostImageDTO>> postImageCache = new ConcurrentHashMap<>();

    public void putImages(Long postCode, List<PostImageDTO> imageList) {
        postImageCache.put(postCode, imageList);
    }

    public List<PostImageDTO> getImages(Long postCode) {
        return postImageCache.get(postCode);
    }

    public void addImages(Long postCode, List<PostImageDTO> cacheImages) {
        postImageCache.compute(postCode, (key, cachedList) -> {
            if (cachedList == null) {
                return new ArrayList<>(cacheImages);
            } else {
                List<PostImageDTO> originCacheList = new ArrayList<>(cachedList);
                originCacheList.addAll(cacheImages); // 오리진 갱신
                return originCacheList;
            }
        });
    }

    public void removeImageAndReorder(Long postCode, String filePath, Long deletedOrder) {
        postImageCache.computeIfPresent(postCode, (key, cachedList) -> {
            List<PostImageDTO> originCacheList = new ArrayList<>(cachedList); // 캐싱된 리스트
            originCacheList.removeIf(postImageDTO -> postImageDTO.getFilePath().equals(filePath)); // 타겟 캐싱 데이터 제거

            for (PostImageDTO postImageDTO : originCacheList) { // 캐싱 데이터 순서 조정
                if (postImageDTO.getOrder() > deletedOrder) {
                    postImageDTO.setOrder(postImageDTO.getOrder() - 1);
                }
            }
            return originCacheList;
        });
    }

    public void updateImagePath(Long postCode, String oldFilePath, String newFilePath) {
        postImageCache.computeIfPresent(postCode, (key, cachedList) -> {
            for (PostImageDTO postImageDTO : cachedList) {
                if (postImageDTO.getFilePath().equals(oldFilePath)) {
                    postImageDTO.setFilePath(newFilePath);
                    break;
                }
            }
            return cachedList;
        });
    }

    public void swapMainImage(Long postCode, Long currentOrder, Long newOrder) {
        postImageCache.computeIfPresent(postCode, (key, cachedList) -> {
            PostImageDTO targetOrderImage = null;
            PostImageDTO zeroOrderImage = null;

            for (PostImageDTO postImageDTO : cachedList) {
                if (Objects.equals(postImageDTO.getOrder(), currentOrder)) {
                    zeroOrderImage = postImageDTO;
                } else if (postImageDTO.getOrder().equals(newOrder)) {
                    targetOrderImage = postImageDTO;
                }
            }

            if (zeroOrderImage != null && targetOrderImage != null) {
                Long tmp = zeroOrderImage.getOrder();
                zeroOrderImage.setOrder(targetOrderImage.getOrder());
                targetOrderImage.setOrder(tmp);
            }

            return cachedList;
        });
    }
}

