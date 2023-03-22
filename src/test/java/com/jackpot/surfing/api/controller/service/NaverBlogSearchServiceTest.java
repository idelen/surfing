package com.jackpot.surfing.api.controller.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.jackpot.surfing.api.client.NaverBlogSearchWebClient;
import com.jackpot.surfing.api.domain.naver.NaverBlogItem;
import com.jackpot.surfing.api.domain.naver.NaverBlogSearchResponse;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.service.NaverBlogSearchService;
import java.net.URI;
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
public class NaverBlogSearchServiceTest {
    @Mock
    private NaverBlogSearchWebClient naverBlogSearchWebClient;

    @InjectMocks
    private NaverBlogSearchService naverBlogSearchService;

    @Test
    void searchBlogsPagingTest() {
        // given
        String query = "surfing";
        String sort = "ACCURACY";
        int page = 1;
        int size = 10;

        BlogSearchCondition blogSearchCondition = new BlogSearchCondition(query, sort, page, size);

        // 검색 결과 생성
        List<NaverBlogItem> items = new ArrayList<>();
        items.add(buildNaverItem("title1", "link1", "description1", "bloggername1", "bloggerlink1", "postdate1"));
        items.add(buildNaverItem("title2", "link2", "description2", "bloggername2", "bloggerlink2", "postdate2"));

        NaverBlogSearchResponse naverBlogSearchResponse = new NaverBlogSearchResponse();
        naverBlogSearchResponse.setItems(items);

        when(naverBlogSearchWebClient.getBlogs(Mockito.any(URI.class))).thenReturn(naverBlogSearchResponse);

        // when
        Page<BlogSearchResultDto> result = naverBlogSearchService.searchBlogsPaging(blogSearchCondition);

        // then
        assertEquals(2, result.getContent().size());
        assertEquals("title1", result.getContent().get(0).getTitle());
        assertEquals("title2", result.getContent().get(1).getTitle());
    }

    private NaverBlogItem buildNaverItem(String title, String link, String description, String bloggername, String bloggerlink, String postdate) {
        NaverBlogItem naverBlogItem = new NaverBlogItem();
        naverBlogItem.setTitle(title);
        naverBlogItem.setLink(link);
        naverBlogItem.setDescription(description);
        naverBlogItem.setBloggername(bloggername);
        naverBlogItem.setBloggerlink(bloggerlink);
        naverBlogItem.setPostdate(postdate);
        return naverBlogItem;
    }
}
