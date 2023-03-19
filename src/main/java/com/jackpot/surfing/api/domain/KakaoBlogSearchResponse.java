package com.jackpot.surfing.api.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoBlogSearchResponse {
    private List<KakaoBlogDocument> documents;
    private KakaoBlogMeta meta;
}





