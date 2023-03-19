package com.jackpot.surfing.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BlogSearchPageAndSizeValidator implements ConstraintValidator<PageAndSizeLimit, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return (value > 0 && value <= 50);
    }
}
