package com.jackpot.surfing.api.controller;

import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchKeywordDto;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.service.BlogSearchKeywordsService;
import com.jackpot.surfing.api.service.KakaoBlogSearchService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blogs")
public class BlogController {
    private final KakaoBlogSearchService kakaoBlogSearchService;

    private final BlogSearchKeywordsService blogSearchKeywordsService;

    @PostMapping("/search/paging")
    @ResponseBody
    public Page<BlogSearchResultDto> searchBlogsPaging(@RequestBody @Valid BlogSearchCondition blogSearchCondition) {
        return kakaoBlogSearchService.searchBlogsPaging(blogSearchCondition);
    }

    @GetMapping("/top10")
    public List<BlogSearchKeywordDto> searchTop10Keywords() {
        return blogSearchKeywordsService.getTop10KeywordsList();
    }
}

