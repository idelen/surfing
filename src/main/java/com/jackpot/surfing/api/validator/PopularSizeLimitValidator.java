package com.jackpot.surfing.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PopularSizeLimitValidator implements ConstraintValidator<PopularSizeLimit, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value > 0 && value <= 10;
    }
}
