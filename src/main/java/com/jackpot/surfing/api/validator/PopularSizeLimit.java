package com.jackpot.surfing.api.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PopularSizeLimitValidator.class)
public @interface PopularSizeLimit {
    String message() default "1에서 10까지의 값만 입력 가능합니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
