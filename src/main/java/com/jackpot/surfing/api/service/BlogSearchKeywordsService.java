package com.jackpot.surfing.api.service;

import com.jackpot.surfing.api.domain.BlogSearchKeywords;
import com.jackpot.surfing.api.domain.BlogSearchKeywordsRepository;
import com.jackpot.surfing.api.dto.BlogSearchKeywordDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BlogSearchKeywordsService {

    private final BlogSearchKeywordsRepository blogSearchKeywordsRepository;

    @Transactional
    public void countUpSearchBlogKeyword(String query) {
        BlogSearchKeywords blogSearchKeywords = blogSearchKeywordsRepository.findByKeyword(query)
            .orElse(new BlogSearchKeywords(query, 0));

        blogSearchKeywords.incrementCount();

        blogSearchKeywordsRepository.save(blogSearchKeywords);
    }

    @Transactional(readOnly = true)
    public List<BlogSearchKeywordDto> getPopularKeywordList(int size) {
        return blogSearchKeywordsRepository.findAll(Sort.by(Sort.Direction.DESC, "count")).stream()
            .limit(size)
            .map(BlogSearchKeywordDto::convert)
            .collect(Collectors.toList());
    }
}
