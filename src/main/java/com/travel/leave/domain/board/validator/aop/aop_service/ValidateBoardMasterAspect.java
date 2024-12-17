package com.travel.leave.domain.board.validator.aop.aop_service;

import com.travel.leave.domain.board.validator.common_validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidateBoardMasterAspect {

    private final BoardValidator boardValidator;

    @Before(value = "@annotation(com.travel.leave.domain.board.validator.aop.aop_annotation.ValidateBoardMaster) && args(postCode, userCode, ..)",
            argNames = "postCode,userCode")
    public void validateMaster(Long postCode, Long userCode) {
        boardValidator.validatePostMaster(postCode, userCode);
    }
}