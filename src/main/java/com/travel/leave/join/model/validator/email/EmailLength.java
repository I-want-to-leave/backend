package com.travel.leave.join.model.validator.email;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailLengthValidator.class)
public @interface EmailLength {
    String message() default "이메일 길이는 70자 이하입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
