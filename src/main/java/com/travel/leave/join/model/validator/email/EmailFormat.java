package com.travel.leave.join.model.validator.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailFormatValidator.class)
public @interface EmailFormat {
    String message() default "이메일 형식이 잘못됐습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
