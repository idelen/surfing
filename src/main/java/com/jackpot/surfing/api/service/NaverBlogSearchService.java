package com.jackpot.surfing.api.service;

import com.jackpot.surfing.api.client.KakaoBlogSearchWebClient;
import com.jackpot.surfing.api.client.NaverBlogSearchWebClient;
import com.jackpot.surfing.api.domain.naver.NaverBlogSearchResponse;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.dto.BlogSearchSortOption;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class NaverBlogSearchService extends BlogSearchService {

    @Value("${naver.api.blog.url}")
    private String BLOG_URL;

    @Value("${naver.api.client.id}")
    private String API_CLIENT_ID;

    @Value("${naver.api.client.secret}")
    private String API_CLIENT_SECRET;

    public static final String X_NAVER_CLIENT_ID = "X-Naver-Client-Id";
    public static final String X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";

    private final NaverBlogSearchWebClient naverBlogSearchWebClient;

    @Override
    public Page<BlogSearchResultDto> searchBlogsPaging(BlogSearchCondition blogSearchCondition) {

        NaverBlogSearchResponse naverBlogSearchResponse = naverBlogSearchWebClient.getBlogs(createUri(blogSearchCondition));

        List<BlogSearchResultDto> resultDtoList =
            Objects.requireNonNull(naverBlogSearchResponse.getItems()).stream()
                .map(BlogSearchResultDto::convertFromNaver)
                .collect(Collectors.toList());

        return new PageImpl<>(resultDtoList, getPageable(blogSearchCondition), naverBlogSearchResponse.getTotal());
    }

    private URI createUri(BlogSearchCondition blogSearchCondition) {
        return UriComponentsBuilder.fromHttpUrl(BLOG_URL)
            .queryParam("query", blogSearchCondition.getQuery())
            .queryParam("display", blogSearchCondition.getSize())
            .queryParam("start", blogSearchCondition.getPage())
            .queryParam("sort", convertSort(blogSearchCondition.getSort()))
            .encode(StandardCharsets.UTF_8)
            .build()
            .toUri();
    }

    private String convertSort(String sort) {
        return BlogSearchSortOption.valueOf(sort).getNaverSortOption();
    }
}
