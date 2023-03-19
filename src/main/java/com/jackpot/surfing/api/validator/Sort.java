package com.jackpot.surfing.api.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BlogSearchSortValidator.class)
public @interface Sort {
    String message() default "정렬 옵션 오류";
    Class[] groups() default {};
    Class[] payload() default {};
}
