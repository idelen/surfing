package com.jackpot.surfing.api.client;

import com.jackpot.surfing.api.domain.naver.NaverBlogSearchResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class NaverBlogSearchWebClient implements BlogSearchWebClient {
    @Value("${naver.api.blog.url}")
    private String BLOG_URL;

    @Value("${naver.api.client.id}")
    private String API_CLIENT_ID;

    @Value("${naver.api.client.secret}")
    private String API_CLIENT_SECRET;

    public static final String X_NAVER_CLIENT_ID = "X-Naver-Client-Id";
    public static final String X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";
    private final WebClient webClient;

    @Override
    public NaverBlogSearchResponse getBlogs(URI uri) {
        return webClient.get()
            .uri(uri)
            .header(X_NAVER_CLIENT_ID, API_CLIENT_ID)
            .header(X_NAVER_CLIENT_SECRET, API_CLIENT_SECRET)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(NaverBlogSearchResponse.class)
            .block();
    }
}
