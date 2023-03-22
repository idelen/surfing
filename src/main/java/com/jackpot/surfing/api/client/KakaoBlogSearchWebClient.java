package com.jackpot.surfing.api.client;

import com.jackpot.surfing.api.domain.kakao.KakaoBlogSearchResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoBlogSearchWebClient implements BlogSearchWebClient {

    private static final String AUTHORIZATION = "Authorization";
    private static final String KAKAO_AK = "KakaoAK ";

    @Value("${kakao.api.key}")
    private String API_KEY;

    private final WebClient webClient;

    @Override
    public KakaoBlogSearchResponse getBlogs(URI uri) {
        return webClient.get()
            .uri(uri)
            .header(AUTHORIZATION, KAKAO_AK + API_KEY)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(KakaoBlogSearchResponse.class)
            .block();
    }
}
