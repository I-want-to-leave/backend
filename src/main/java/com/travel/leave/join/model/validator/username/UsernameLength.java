package com.travel.leave.join.model.validator.username;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameLengthValidator.class)
public @interface UsernameLength {
    String message() default "아이디 길이는 8~25자입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
