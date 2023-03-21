package com.jackpot.surfing.api.service;

import com.jackpot.surfing.api.client.KakaoBlogSearchWebClient;
import com.jackpot.surfing.api.domain.kakao.KakaoBlogSearchResponse;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.dto.BlogSearchSortOption;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoBlogSearchService extends BlogSearchService {

    @Value("${kakao.api.blog.url}")
    private String BLOG_URL;
    private final BlogSearchKeywordsService blogSearchKeywordsService;
    private final KakaoBlogSearchWebClient kakaoBlogSearchWebClient;

    @Override
    public Page<BlogSearchResultDto> searchBlogsPaging(BlogSearchCondition blogSearchCondition) {
        KakaoBlogSearchResponse kakaoBlogSearchResponse = kakaoBlogSearchWebClient.getBlogs(createUri(blogSearchCondition));

        log.info("[BlogSearchKeywordsService] before save. query : " + blogSearchCondition.getQuery());
        blogSearchKeywordsService.countSearchBlogKeyword(blogSearchCondition.getQuery());
        log.info("[BlogSearchKeywordsService] after save. query : " + blogSearchCondition.getQuery());

        List<BlogSearchResultDto> resultDtoList =
            Objects.requireNonNull(kakaoBlogSearchResponse.getDocuments()).stream()
                .map(BlogSearchResultDto::convertFromKakao)
                .collect(Collectors.toList());

        return new PageImpl<>(resultDtoList, getPageable(blogSearchCondition),
            kakaoBlogSearchResponse.getMeta().getPageable_count());
    }

    private URI createUri(BlogSearchCondition blogSearchCondition) {
        return UriComponentsBuilder.fromHttpUrl(BLOG_URL)
            .queryParam("query", blogSearchCondition.getQuery())
            .queryParam("sort", convertSort(blogSearchCondition.getSort()))
            .queryParam("page", blogSearchCondition.getPage())
            .queryParam("size", blogSearchCondition.getSize())
            .encode(StandardCharsets.UTF_8)
            .build()
            .toUri();
    }

    private String convertSort(String sort) {
        return BlogSearchSortOption.valueOf(sort).getKakaoSortOption();
    }
}

