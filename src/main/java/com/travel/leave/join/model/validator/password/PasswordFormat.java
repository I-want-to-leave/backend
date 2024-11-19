package com.travel.leave.join.model.validator.password;

import com.travel.leave.join.model.validator.nickname.NicknameFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordFormatValidator.class)
public @interface PasswordFormat {
    String message() default "패스워드 형식이 잘못됐습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
