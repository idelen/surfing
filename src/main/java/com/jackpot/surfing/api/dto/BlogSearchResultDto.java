package com.jackpot.surfing.api.dto;

import com.jackpot.surfing.api.domain.naver.NaverBlogItem;
import com.jackpot.surfing.api.domain.kakao.KakaoBlogDocument;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlogSearchResultDto {
    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String thumbnail;
    private String datetime;

    public static BlogSearchResultDto convertFromKakao(KakaoBlogDocument document) {
        return BlogSearchResultDto.builder()
            .title(document.getTitle())
            .contents(document.getContents())
            .url(document.getUrl())
            .blogName(document.getBlogName())
            .thumbnail(document.getThumbnail())
            .datetime(document.getDatetime())
            .build();
    }

    public static BlogSearchResultDto convertFromNaver(NaverBlogItem item) {
        return BlogSearchResultDto.builder()
            .title(item.getTitle())
            .contents(item.getDescription())
            .url(item.getLink())
            .blogName(item.getBloggername())
            .datetime(item.getPosddate())
            .build();
    }
}
