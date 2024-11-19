package com.travel.leave.join.model.validator.nickname;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknameFormatValidator implements ConstraintValidator<NicknameFormat, String> {
    private static final String NICKNAME_REGEX = "^[가-힣]+$";

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext constraintValidatorContext) {
        return nickname.matches(NICKNAME_REGEX);
    }
}
