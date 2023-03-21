package com.jackpot.surfing.api.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum BlogSearchSortOption {
    ACCURACY("ACCURACY", "정확도순", "accuracy", "sim"),
    RECENCY("RECENCY", "최신순", "receny", "date");

    private final String code;
    private final String korDescription;
    private final String kakaoSortOption;
    private final String naverSortOption;

    BlogSearchSortOption(String code, String korDescription, String kakaoSortOption, String naverSortOption) {
        this.code = code;
        this.korDescription = korDescription;
        this.kakaoSortOption = kakaoSortOption;
        this.naverSortOption = naverSortOption;
    }

    public static List<String> getAllCode() {
        return Arrays.stream(BlogSearchSortOption.values())
            .map(BlogSearchSortOption::getCode)
            .collect(Collectors.toList());
    }
}
