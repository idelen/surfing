package com.jackpot.surfing.api.controller.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.jackpot.surfing.api.application.BlogSearchApplication;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.service.KakaoBlogSearchService;
import com.jackpot.surfing.api.service.NaverBlogSearchService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ExtendWith(MockitoExtension.class)
public class BlogSearchApplicationTest {

    @Mock
    private KakaoBlogSearchService kakaoBlogSearchService;

    @Mock
    private NaverBlogSearchService naverBlogSearchService;

    @InjectMocks
    private BlogSearchApplication blogSearchApplication;

    @Test
    public void testSearchBlogsPaging_WhenKakaoIsNormal_ReturnsBlogSearchResults() {
        // given
        BlogSearchCondition condition = new BlogSearchCondition();
        Page<BlogSearchResultDto> expected = new PageImpl<>(Collections.emptyList());
        when(kakaoBlogSearchService.searchBlogsPaging(condition)).thenReturn(expected);

        // when
        Page<BlogSearchResultDto> result = blogSearchApplication.searchBlogsPaging(condition);

        // then
        assertEquals(expected, result);
        verify(kakaoBlogSearchService).searchBlogsPaging(condition);
        verifyNoMoreInteractions(naverBlogSearchService);
    }

    @Test
    public void searchBlogsPaging_WhenKakaoIsNotNormal_ReturnsNaverBlogSearchResults() {
        // given
        BlogSearchCondition condition = new BlogSearchCondition();
        WebClientResponseException exception = new WebClientResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", null, null, null);
        when(kakaoBlogSearchService.searchBlogsPaging(condition)).thenThrow(exception);
        Page<BlogSearchResultDto> expected = new PageImpl<>(Collections.emptyList());
        when(naverBlogSearchService.searchBlogsPaging(condition)).thenReturn(expected);

        // when
        Page<BlogSearchResultDto> result = blogSearchApplication.searchBlogsPaging(condition);

        // then
        assertEquals(expected, result);
        verify(kakaoBlogSearchService).searchBlogsPaging(condition);
        verify(naverBlogSearchService).searchBlogsPaging(condition);
    }

}
