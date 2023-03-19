package com.jackpot.surfing.api.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BlogSearchPageAndSizeValidator.class)
public @interface PageAndSizeLimit {
    String message() default "1에서 50까지의 값만 입력 가능합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
