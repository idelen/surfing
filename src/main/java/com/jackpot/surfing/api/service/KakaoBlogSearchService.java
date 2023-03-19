package com.jackpot.surfing.api.service;

import com.jackpot.surfing.api.domain.KakaoBlogSearchResponse;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class KakaoBlogSearchService {

//    @Value("${kakao.api.blog.url}")
    private String blogUrl = "https://dapi.kakao.com/v2/search/blog";

//    @Value("${kakao.api.key}")
    private String apiKey = "d01de35bce1eeb65cbc599f0693c3aab";

    private final RestTemplate restTemplate;

    private final BlogSearchKeywordsService blogSearchKeywordsService;

    public KakaoBlogSearchResponse searchBlogs(String query, int page, int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + apiKey);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(blogUrl)
            .queryParam("query", query)
            .queryParam("page", page)
            .queryParam("size", size)
            .encode(StandardCharsets.UTF_8)
            .build();

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<KakaoBlogSearchResponse> responseEntity =
            restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, requestEntity, KakaoBlogSearchResponse.class);

        return responseEntity.getBody();
    }

    public Page<BlogSearchResultDto> searchBlogsPaging(BlogSearchCondition blogSearchCondition) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + apiKey);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(blogUrl)
            .queryParam("query", blogSearchCondition.getQuery())
            .queryParam("sort", blogSearchCondition.getSort())
            .queryParam("page", blogSearchCondition.getPage())
            .queryParam("size", blogSearchCondition.getSize())
            .encode(StandardCharsets.UTF_8)
            .build();

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<KakaoBlogSearchResponse> responseEntity =
            restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, requestEntity, KakaoBlogSearchResponse.class);

        blogSearchKeywordsService.countSearchBlogKeyword(blogSearchCondition.getQuery());

        Pageable pageable = PageRequest.of(blogSearchCondition.getPage() - 1, blogSearchCondition.getSize());

        List<BlogSearchResultDto> resultDtoList = responseEntity.getBody().getDocuments().stream()
            .map(BlogSearchResultDto::convertFromKakao)
            .collect(Collectors.toList());

        return new PageImpl<>(resultDtoList, pageable, responseEntity.getBody().getMeta().getPageable_count());
    }
}

