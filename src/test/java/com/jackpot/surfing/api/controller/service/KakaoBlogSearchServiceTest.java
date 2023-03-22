package com.jackpot.surfing.api.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.jackpot.surfing.api.client.KakaoBlogSearchWebClient;
import com.jackpot.surfing.api.domain.kakao.KakaoBlogDocument;
import com.jackpot.surfing.api.domain.kakao.KakaoBlogMeta;
import com.jackpot.surfing.api.domain.kakao.KakaoBlogSearchResponse;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.service.KakaoBlogSearchService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
public class KakaoBlogSearchServiceTest {
    @Mock
    private KakaoBlogSearchWebClient kakaoBlogSearchWebClient;

    @InjectMocks
    private KakaoBlogSearchService kakaoBlogSearchService;

    @Test
    void searchBlogsPagingTest() {
        // given
        String query = "surfing";
        String sort = "ACCURACY";
        int page = 1;
        int size = 10;

        BlogSearchCondition blogSearchCondition = new BlogSearchCondition(query, sort, page, size);

        // 검색 결과 생성
        List<KakaoBlogDocument> documents = new ArrayList<>();

        documents.add(buildKakaoDocument("title1", "content1", "url1", "blogname1", "thumnail1", LocalDateTime.now().toString()));
        documents.add(buildKakaoDocument("title2", "content2", "url2", "blogname2", "thumnail2", LocalDateTime.now().toString()));

        KakaoBlogMeta meta = new KakaoBlogMeta();

        KakaoBlogSearchResponse kakaoBlogSearchResponse = new KakaoBlogSearchResponse();
        kakaoBlogSearchResponse.setDocuments(documents);
        kakaoBlogSearchResponse.setMeta(meta);

        when(kakaoBlogSearchWebClient.getBlogs(Mockito.any(URI.class))).thenReturn(kakaoBlogSearchResponse);

        // when
        Page<BlogSearchResultDto> result = kakaoBlogSearchService.searchBlogsPaging(blogSearchCondition);

        // then
        assertEquals(2, result.getContent().size());
        assertEquals("title1", result.getContent().get(0).getTitle());
        assertEquals("title2", result.getContent().get(1).getTitle());
    }

    private KakaoBlogDocument buildKakaoDocument(String title, String content, String url, String blogName, String thumbnail, String dateTime) {
        KakaoBlogDocument kakaoBlogDocument = new KakaoBlogDocument();
        kakaoBlogDocument.setTitle(title);
        kakaoBlogDocument.setContents(content);
        kakaoBlogDocument.setUrl(url);
        kakaoBlogDocument.setBlogName(blogName);
        kakaoBlogDocument.setThumbnail(thumbnail);
        kakaoBlogDocument.setDatetime(dateTime);
        return kakaoBlogDocument;
    }
}
