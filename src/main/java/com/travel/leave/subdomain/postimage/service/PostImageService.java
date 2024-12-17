package com.travel.leave.subdomain.postimage.service;

import com.travel.leave.domain.board.dto.request.PostImageUpdateRequestDTO;
import com.travel.leave.domain.board.dto.response.postdetail.PostImageDTO;
import com.travel.leave.domain.board.service.post_image.CachePostImageService;
import com.travel.leave.domain.board.validator.aop.aop_annotation.ValidateBoardMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final CachePostImageService cachePostImageService;

    @Transactional @ValidateBoardMaster @SuppressWarnings("unused")
    public List<PostImageDTO> uploadPostImages(Long postCode, Long userCode, List<String> rawImages) {
        return cachePostImageService.addImages(postCode, rawImages);
    } // userCode 는 aop 에서 처리

    @Transactional @ValidateBoardMaster @SuppressWarnings("unused")
    public List<PostImageDTO> deleteImage(Long postCode, Long userCode, String filePath) {
        return cachePostImageService.removeImage(postCode, filePath);
    } // userCode 는 aop 에서 처리

    @ValidateBoardMaster @SuppressWarnings("unused")
    public List<PostImageDTO> updateImage(Long postCode, Long userCode, PostImageUpdateRequestDTO updateRequestDTO) {
        return cachePostImageService.updateImage(postCode, updateRequestDTO);
    } // userCode 는 aop 에서 처리

    @ValidateBoardMaster @SuppressWarnings("unused")
    public List<PostImageDTO> setMainImage(Long postCode, Long userCode, Long targetOrder) {
        return cachePostImageService.swapMainImage(postCode, targetOrder);
    } // userCode 는 aop 에서 처리

    public List<PostImageDTO> getImagesByPostCode(Long postCode) {
        return cachePostImageService.getImages(postCode);
    }
}