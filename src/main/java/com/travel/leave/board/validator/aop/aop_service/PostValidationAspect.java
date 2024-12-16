package com.travel.leave.board.validator.aop.aop_service;

import com.travel.leave.board.validator.common_validator.PostValidator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PostValidationAspect {

    private final PostValidator postValidator;

    @Before(value = "execution(* com.travel.leave.board.service.post_image.PostImageService.uploadPostImages(..)) && args(postCode, userCode, ..)", argNames = "postCode,userCode")
    public void validateUploadPostImages(Long postCode, Long userCode) {
        postValidator.validatePostMaster(postCode, userCode);
    }

    @Before(value = "execution(* com.travel.leave.board.service.post_image.PostImageService.deleteImage(..)) && args(postCode, userCode, ..)", argNames = "postCode,userCode")
    public void validateDeleteImage(Long postCode, Long userCode) {
        postValidator.validatePostMaster(postCode, userCode);
    }

    @Before(value = "execution(* com.travel.leave.board.service.post_image.PostImageService.updateImage(..)) && args(postCode, userCode, ..)", argNames = "postCode,userCode")
    public void validateUpdateImage(Long postCode, Long userCode) {
        postValidator.validatePostMaster(postCode, userCode);
    }

    @Before(value = "execution(* com.travel.leave.board.service.post_image.PostImageService.setMainImage(..)) && args(postCode, userCode, ..)", argNames = "postCode,userCode")
    public void validateSetMainImage(Long postCode, Long userCode) {
        postValidator.validatePostMaster(postCode, userCode);
    }
}
