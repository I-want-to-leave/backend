package com.travel.leave.join.model.validator.username;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameFormatValidator implements ConstraintValidator<UsernameFormat, String> {
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]+";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return username.matches(USERNAME_REGEX);
    }

}
