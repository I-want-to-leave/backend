package com.travel.leave.join.model.validator.username;

import com.travel.leave.exception.message.ExceptionMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameLengthValidator implements ConstraintValidator<UsernameLength, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return username.length() >= 8 && username.length() <= 25;
    }
}
