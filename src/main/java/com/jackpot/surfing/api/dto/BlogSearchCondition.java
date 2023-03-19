package com.jackpot.surfing.api.dto;

import com.jackpot.surfing.api.validator.PageAndSizeLimit;
import com.jackpot.surfing.api.validator.Sort;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BlogSearchCondition {
    @NotBlank
    private String query;
    @Sort
    private String sort;
    @PageAndSizeLimit
    private int page;
    @PageAndSizeLimit
    private int size;
}
