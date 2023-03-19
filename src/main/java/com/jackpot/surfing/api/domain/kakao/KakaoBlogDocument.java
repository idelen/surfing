package com.jackpot.surfing.api.domain.kakao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoBlogDocument {
    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String thumbnail;
    private String datetime;
}
