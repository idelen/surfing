package com.jackpot.surfing.api.dto;

import com.jackpot.surfing.api.domain.naver.NaverBlogItem;
import com.jackpot.surfing.api.domain.kakao.KakaoBlogDocument;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            .datetime(convertDateTime(item.getPostdate()))
            .build();
    }

    private static String convertDateTime(String datetime) {
        try {
            SimpleDateFormat naverDateFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat kakaoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

            Date naverDateTime = naverDateFormat.parse(datetime);

            return kakaoDateFormat.format(naverDateTime);
        } catch (ParseException e) {
            log.warn("[BlogSearchResultDto] Invalid Date format - Datetime : " + datetime);
            return datetime;
        }
    }
}
