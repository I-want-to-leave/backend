package com.travel.leave.join.model.validator.nickname;

import com.travel.leave.exception.message.ExceptionMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknameLengthValidator implements ConstraintValidator<NicknameLength, String> {
    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext constraintValidatorContext) {
        return nickname.length() >= 2 && nickname.length() <= 4;
    }
}
