package com.jackpot.surfing.api.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum BlogSearchSortOption {
    ACCURACY("accuracy"),
    RECENCY("receny");

    private final String sortOption;

    BlogSearchSortOption(String sortOption) {
        this.sortOption = sortOption;
    }

    public static List<String> getValues() {
        return Arrays.stream(BlogSearchSortOption.values())
            .map(blogSearchSortOption -> blogSearchSortOption.getSortOption())
            .collect(Collectors.toList());
    }
}
