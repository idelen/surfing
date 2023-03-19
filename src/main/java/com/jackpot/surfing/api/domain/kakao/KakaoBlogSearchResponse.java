package com.jackpot.surfing.api.domain.kakao;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoBlogSearchResponse {
    private List<KakaoBlogDocument> documents;
    private KakaoBlogMeta meta;
}





