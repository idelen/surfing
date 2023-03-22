package com.jackpot.surfing.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

public class PopularSizeLimitValidator implements ConstraintValidator<PopularSizeLimit, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return !ObjectUtils.isEmpty(value) && value > 0 && value <= 10;
    }
}
