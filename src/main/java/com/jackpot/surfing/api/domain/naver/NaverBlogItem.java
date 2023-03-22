package com.jackpot.surfing.api.domain.naver;

import lombok.Data;

@Data
public class NaverBlogItem {
    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    private String postdate;
}
