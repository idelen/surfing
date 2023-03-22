package com.jackpot.surfing.api.dto;

import com.jackpot.surfing.api.domain.BlogSearchKeywords;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlogSearchKeywordDto {
    private String keyword;
    private int count;

    public static BlogSearchKeywordDto convert(BlogSearchKeywords blogSearchKeywords) {
        return BlogSearchKeywordDto.builder()
            .keyword(blogSearchKeywords.getKeyword())
            .count(blogSearchKeywords.getCount())
            .build();
    }
}
