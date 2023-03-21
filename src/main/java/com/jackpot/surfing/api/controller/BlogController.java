package com.jackpot.surfing.api.controller;

import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchKeywordDto;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.service.BlogSearchKeywordsService;
import com.jackpot.surfing.api.service.KakaoBlogSearchService;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/blogs")
public class BlogController {
    private final KakaoBlogSearchService kakaoBlogSearchService;

    private final BlogSearchKeywordsService blogSearchKeywordsService;

    private final String POPULAR_SEARCH_DEFAULT_SIZE = "10";

    @PostMapping("/search/paging")
    @ResponseBody
    public ResponseEntity<Page<BlogSearchResultDto>> searchBlogsPaging(@RequestBody @Valid BlogSearchCondition blogSearchCondition) {
        try {
            return ResponseEntity.ok(kakaoBlogSearchService.searchBlogsPaging(blogSearchCondition));
        } catch (Exception e) {
            log.error("[BlogController] error : " + e);

            // todo : status 변경필요
            return ResponseEntity.badRequest().body(new PageImpl<>(Collections.emptyList()));
        }
    }

    @GetMapping("/popular-search")
    public ResponseEntity<List<BlogSearchKeywordDto>> getPopularKeywordList(@RequestParam(defaultValue = POPULAR_SEARCH_DEFAULT_SIZE) int size) {
        return ResponseEntity.ok(blogSearchKeywordsService.getPopularKeywordList(size));
    }
}

