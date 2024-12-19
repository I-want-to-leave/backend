package com.travel.leave.domain.board.validator.aop.aop_aspect;

import com.travel.leave.domain.board.validator.common_validator.BoardCommentValidator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidateCommentMasterAspect {

    private final BoardCommentValidator boardCommentValidator;

    @Before(value = "@annotation(com.travel.leave.domain.board.validator.aop.aop_annotation.ValidateCommentMaster) && args(commentCode, userCode, ..)",
            argNames = "commentCode,userCode")
    public void validateMaster(Long commentCode, Long userCode) {
        boardCommentValidator.validateOwnership(commentCode, userCode);
    }
}
