package com.jackpot.surfing.api.service;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
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

    private final NaverBlogSearchService naverBlogSearchService;

    public Page<BlogSearchResultDto> searchBlogsPaging(BlogSearchCondition blogSearchCondition) {
        try {
            ResponseEntity<KakaoBlogSearchResponse> kakaoBlogResponse = getKakaoBlogs(
                blogSearchCondition);

            blogSearchKeywordsService.countSearchBlogKeyword(blogSearchCondition.getQuery());

            List<BlogSearchResultDto> resultDtoList =
                Objects.requireNonNull(kakaoBlogResponse.getBody()).getDocuments().stream()
                    .map(BlogSearchResultDto::convertFromKakao)
                    .collect(Collectors.toList());

            return new PageImpl<>(resultDtoList, getPageable(blogSearchCondition),
                kakaoBlogResponse.getBody().getMeta().getPageable_count());
        } catch (HttpClientErrorException e) {
            log.error("[KakaoBlogSearchService] client error : " + e);
            // todo : 테스트용 추가. 이후 삭제할것.
            return naverBlogSearchService.searchBlogsPaging(blogSearchCondition);
//            throw e;
        } catch (HttpServerErrorException e) {
            log.error("[KakaoBlogSearchService] server error : " + e);
            return naverBlogSearchService.searchBlogsPaging(blogSearchCondition);
        } catch (Exception e) {
            log.error("[KakaoBlogSearchService] unknown error : " + e);
            throw e;
        }
    }

    private ResponseEntity<KakaoBlogSearchResponse> getKakaoBlogs(BlogSearchCondition blogSearchCondition) {
        RequestEntity<Void> requestEntity = RequestEntity.get(createUri(blogSearchCondition))
            .header(AUTHORIZATION, KAKAO_AK + API_KEY)
            .build();

        return restTemplate.exchange(requestEntity, KakaoBlogSearchResponse.class);
    }

    private URI createUri(BlogSearchCondition blogSearchCondition) {
        return UriComponentsBuilder.fromHttpUrl(BLOG_URL)
            .queryParam("query", blogSearchCondition.getQuery())
            .queryParam("sort", BlogSearchSortOption.valueOf(blogSearchCondition.getSort()).getKakaoSortOption())
            .queryParam("page", blogSearchCondition.getPage())
            .queryParam("size", blogSearchCondition.getSize())
            .encode(StandardCharsets.UTF_8)
            .build()
            .toUri();
    }

    private static Pageable getPageable(BlogSearchCondition blogSearchCondition) {
        Pageable pageable = PageRequest.of(blogSearchCondition.getPage() - 1, blogSearchCondition.getSize());
        return pageable;
    }
}

