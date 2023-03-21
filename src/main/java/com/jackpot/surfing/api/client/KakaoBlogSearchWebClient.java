package com.jackpot.surfing.api.client;

import com.jackpot.surfing.api.domain.kakao.KakaoBlogSearchResponse;
import com.jackpot.surfing.api.domain.naver.NaverBlogSearchResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoBlogSearchWebClient implements BlogSearchWebClient {

    private static final String AUTHORIZATION = "Authorization";
    private static final String KAKAO_AK = "123";

    @Value("${kakao.api.key}")
    private String API_KEY;

    private final WebClient webClient;

//    @Override
//    public KakaoBlogSearchResponse getBlogs(URI uri) {
//        return webClient.get()
//            .uri(uri)
//            .header(AUTHORIZATION, KAKAO_AK + API_KEY)
//            .accept(MediaType.APPLICATION_JSON)
//            .exchangeToMono(response ->
//                response.bodyToMono(KakaoBlogSearchResponse.class)
//                    .map(validReqVO -> {
//                        if (response.statusCode().is4xxClientError()) {
//                            log.error("API 요청 중 4xx 에러가 발생했습니다. 요청 데이터를 확인해주세요.");
//                            throw new HttpClientErrorException(response.statusCode());
//                        }
//
//                        log.info("API 요청에 성공했습니다.");
//                        return validReqVO;
//                    }))
//            .block();
//    }

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
