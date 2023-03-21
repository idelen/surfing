package com.jackpot.surfing.api.domain.naver;

import java.util.List;
import lombok.Data;

@Data
public class NaverBlogSearchResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverBlogItem> items;
}
