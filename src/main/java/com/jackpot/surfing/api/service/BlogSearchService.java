package com.jackpot.surfing.api.service;

import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public abstract class BlogSearchService {
    public abstract Page<BlogSearchResultDto> searchBlogsPaging(BlogSearchCondition blogSearchCondition);

    public Pageable getPageable(BlogSearchCondition blogSearchCondition) {
        return PageRequest.of(blogSearchCondition.getPage() - 1, blogSearchCondition.getSize());
    }
}
