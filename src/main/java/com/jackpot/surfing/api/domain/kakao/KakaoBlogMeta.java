package com.jackpot.surfing.api.domain.kakao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoBlogMeta {
    private int total_count;
    private int pageable_count;
    private boolean is_end;
}