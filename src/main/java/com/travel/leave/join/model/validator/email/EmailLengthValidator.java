package com.travel.leave.join.model.validator.email;

import com.travel.leave.exception.message.ExceptionMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Email;

public class EmailLengthValidator implements ConstraintValidator<EmailLength, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email.length() < 70;
    }
}
