package com.jackpot.surfing.api.validator;

import com.jackpot.surfing.api.dto.BlogSearchSortOption;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BlogSearchSortValidator implements ConstraintValidator<Sort, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return BlogSearchSortOption.getValues().contains(value);
    }
}
