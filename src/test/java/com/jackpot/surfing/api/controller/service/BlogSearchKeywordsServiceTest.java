package com.jackpot.surfing.api.controller.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jackpot.surfing.api.domain.BlogSearchKeywords;
import com.jackpot.surfing.api.domain.BlogSearchKeywordsRepository;
import com.jackpot.surfing.api.dto.BlogSearchKeywordDto;
import com.jackpot.surfing.api.service.BlogSearchKeywordsService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class BlogSearchKeywordsServiceTest {

    @Mock
    private BlogSearchKeywordsRepository blogSearchKeywordsRepository;

    @InjectMocks
    private BlogSearchKeywordsService blogSearchKeywordsService;

    @Test
    public void countUpSearchBlogKeywordTest() {
        String keyword = "test";
        BlogSearchKeywords blogSearchKeywords = new BlogSearchKeywords(keyword, 0);
        when(blogSearchKeywordsRepository.findByKeyword(keyword)).thenReturn(Optional.of(blogSearchKeywords));

        blogSearchKeywordsService.countUpSearchBlogKeyword(keyword);

        verify(blogSearchKeywordsRepository, times(1)).findByKeyword(keyword);
        verify(blogSearchKeywordsRepository, times(1)).save(blogSearchKeywords);
    }

    @Test
    public void getPopularKeywordListTest_SortDirectionDescForCount() {
        // given
        List<BlogSearchKeywords> blogSearchKeywordsList = new ArrayList<>();
        blogSearchKeywordsList.add(new BlogSearchKeywords("test1", 10));
        blogSearchKeywordsList.add(new BlogSearchKeywords("test2", 20));
        blogSearchKeywordsList.add(new BlogSearchKeywords("test3", 30));

        blogSearchKeywordsList.sort(new Comparator<BlogSearchKeywords>() {
            @Override
            public int compare(BlogSearchKeywords o1, BlogSearchKeywords o2) {
                return o2.getCount() - o1.getCount();
            }
        });

        when(blogSearchKeywordsRepository.findAll(Sort.by(Sort.Direction.DESC, "count"))).thenReturn(blogSearchKeywordsList);

        // when
        List<BlogSearchKeywordDto> popularKeywords = blogSearchKeywordsService.getPopularKeywordList(2);

        // then
        assertThat(popularKeywords).hasSize(2);
        assertThat(popularKeywords.get(0).getKeyword()).isEqualTo("test3");
        assertThat(popularKeywords.get(0).getCount()).isEqualTo(30);
        assertThat(popularKeywords.get(1).getKeyword()).isEqualTo("test2");
        assertThat(popularKeywords.get(1).getCount()).isEqualTo(20);
    }
}
