package com.jackpot.surfing.api.dto;

import lombok.Getter;

@Getter
public class BlogSearchCondition {
    private String query;
    private String sort = "accuracy";
    private int page = 1;
    private int size = 10;
}
