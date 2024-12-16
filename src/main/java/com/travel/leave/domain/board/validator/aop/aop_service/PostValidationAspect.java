package com.travel.leave.domain.board.validator.aop.aop_service;

import com.travel.leave.domain.board.validator.common_validator.PostValidator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PostValidationAspect {

    private final PostValidator postValidator;

    @Before(value = "execution(* com.travel.leave.subdomain.postimage.service.PostImageService.uploadPostImages(..)) && args(postCode, userCode, ..)", argNames = "postCode,userCode")
    public void validateUploadPostImages(Long postCode, Long userCode) {
        postValidator.validatePostMaster(postCode, userCode);
    }

    @Before(value = "execution(* com.travel.leave.subdomain.postimage.service.PostImageService.deleteImage(..)) && args(postCode, userCode, ..)", argNames = "postCode,userCode")
    public void validateDeleteImage(Long postCode, Long userCode) {
        postValidator.validatePostMaster(postCode, userCode);
    }

    @Before(value = "execution(* com.travel.leave.subdomain.postimage.service.PostImageService.updateImage(..)) && args(postCode, userCode, ..)", argNames = "postCode,userCode")
    public void validateUpdateImage(Long postCode, Long userCode) {
        postValidator.validatePostMaster(postCode, userCode);
    }

    @Before(value = "execution(* com.travel.leave.subdomain.postimage.service.PostImageService.setMainImage(..)) && args(postCode, userCode, ..)", argNames = "postCode,userCode")
    public void validateSetMainImage(Long postCode, Long userCode) {
        postValidator.validatePostMaster(postCode, userCode);
    }
}
