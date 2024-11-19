package com.travel.leave.join.model.validator.nickname;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameLengthValidator.class)
public @interface NicknameLength {
    String message() default "이름 길이는 성+이름 2~4자 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
