package com.jackpot.surfing.api.controller;

import com.jackpot.surfing.api.domain.KakaoBlogSearchResponse;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.service.KakaoBlogSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    private final KakaoBlogSearchService kakaoBlogSearchService;

    public BlogController(KakaoBlogSearchService kakaoBlogSearchService) {
        this.kakaoBlogSearchService = kakaoBlogSearchService;
    }

    @GetMapping("/search")
    public KakaoBlogSearchResponse searchBlogs(@RequestParam String query, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable test = PageRequest.of(page-1, size);

        return kakaoBlogSearchService.searchBlogs(query, page, size);
    }

    @GetMapping("/search/paging")
    @ResponseBody
    public Page<BlogSearchResultDto> searchBlogsPaging(@RequestBody BlogSearchCondition blogSearchCondition) {
        return kakaoBlogSearchService.searchBlogsPaging(blogSearchCondition);
    }
}

