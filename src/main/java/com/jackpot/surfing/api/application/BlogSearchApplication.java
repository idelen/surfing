package com.jackpot.surfing.api.application;

import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.service.KakaoBlogSearchService;
import com.jackpot.surfing.api.service.NaverBlogSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogSearchApplication {

    private final KakaoBlogSearchService kakaoBlogSearchService;
    private final NaverBlogSearchService naverBlogSearchService;

    public Page<BlogSearchResultDto> searchBlogsPaging(BlogSearchCondition blogSearchCondition) {
        try {
            return kakaoBlogSearchService.searchBlogsPaging(blogSearchCondition);
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                log.error("[BlogSearchApplication] client error : " + e.getStackTrace());
                throw e;
            }

            if (e.getStatusCode().is5xxServerError()) {
                log.error("[BlogSearchApplication] server error : " + e);
                log.info("[BlogSearchApplication] Retry to Naver.");
                return naverBlogSearchService.searchBlogsPaging(blogSearchCondition);
            }

            log.error("[BlogSearchApplication] Unknown error : " + e);
            throw e;
        }
    }
}
