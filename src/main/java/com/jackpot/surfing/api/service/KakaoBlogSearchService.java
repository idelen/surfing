package com.jackpot.surfing.api.service;

import com.jackpot.surfing.api.domain.kakao.KakaoBlogSearchResponse;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class KakaoBlogSearchService {

    public static final String AUTHORIZATION = "Authorization";
    public static final String KAKAO_AK = "KakaoAK ";
    @Value("${kakao.api.blog.url}")
    private String BLOG_URL;

    @Value("${kakao.api.key}")
    private String API_KEY;

    private final RestTemplate restTemplate;

    private final BlogSearchKeywordsService blogSearchKeywordsService;

    public Page<BlogSearchResultDto> searchBlogsPaging(BlogSearchCondition blogSearchCondition) {
        ResponseEntity<KakaoBlogSearchResponse> kakaoBlogResponse = getKakaoBlogs(blogSearchCondition);
        blogSearchKeywordsService.countSearchBlogKeyword(blogSearchCondition.getQuery());

        List<BlogSearchResultDto> resultDtoList =
            Objects.requireNonNull(kakaoBlogResponse.getBody()).getDocuments().stream()
                .map(BlogSearchResultDto::convertFromKakao)
                .collect(Collectors.toList());

        return new PageImpl<>(resultDtoList, getPageable(blogSearchCondition), kakaoBlogResponse.getBody().getMeta().getPageable_count());
    }

    private static Pageable getPageable(BlogSearchCondition blogSearchCondition) {
        Pageable pageable = PageRequest.of(blogSearchCondition.getPage() - 1, blogSearchCondition.getSize());
        return pageable;
    }

    private ResponseEntity<KakaoBlogSearchResponse> getKakaoBlogs(BlogSearchCondition blogSearchCondition) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, KAKAO_AK + API_KEY);

        return restTemplate.exchange(createUri(blogSearchCondition), HttpMethod.GET,
            new HttpEntity<>(headers), KakaoBlogSearchResponse.class);
    }

    private URI createUri(BlogSearchCondition blogSearchCondition) {
        return UriComponentsBuilder.fromHttpUrl(BLOG_URL)
            .queryParam("query", blogSearchCondition.getQuery())
            .queryParam("sort", blogSearchCondition.getSort())
            .queryParam("page", blogSearchCondition.getPage())
            .queryParam("size", blogSearchCondition.getSize())
            .encode(StandardCharsets.UTF_8)
            .build()
            .toUri();
    }
}

