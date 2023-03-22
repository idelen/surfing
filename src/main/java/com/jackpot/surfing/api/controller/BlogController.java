package com.jackpot.surfing.api.controller;

import com.jackpot.surfing.api.application.BlogSearchApplication;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import com.jackpot.surfing.api.dto.BlogSearchKeywordDto;
import com.jackpot.surfing.api.dto.BlogSearchResultDto;
import com.jackpot.surfing.api.service.BlogSearchKeywordsService;
import com.jackpot.surfing.api.validator.PopularSizeLimit;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {
    private final BlogSearchApplication blogSearchApplication;

    private final BlogSearchKeywordsService blogSearchKeywordsService;

    private final String POPULAR_SEARCH_DEFAULT_SIZE = "10";

    @PostMapping("/search/paging")
    @ResponseBody
    public ResponseEntity<Page<BlogSearchResultDto>> searchBlogsPaging(@RequestBody @Valid BlogSearchCondition blogSearchCondition) {
        try {
            log.info("[BlogController] before save. query : "
                + blogSearchCondition.getQuery());
            blogSearchKeywordsService.countUpSearchBlogKeyword(blogSearchCondition.getQuery());
            log.info("[BlogController] after save. query : "
                + blogSearchCondition.getQuery());

            return ResponseEntity.ok(blogSearchApplication.searchBlogsPaging(blogSearchCondition));
        } catch (Exception e) {
            log.error("[BlogController] Unknown Error : " + e);
            return ResponseEntity.internalServerError().body(new PageImpl<>(Collections.emptyList()));
        }
    }

    @GetMapping("/popular-search")
    public ResponseEntity<List<BlogSearchKeywordDto>> getPopularKeywordList(@RequestParam(defaultValue = POPULAR_SEARCH_DEFAULT_SIZE) @PopularSizeLimit int size) {
        try {
            return ResponseEntity.ok(blogSearchKeywordsService.getPopularKeywordList(size));
        }
        catch (Exception e) {
            log.error("[BlogController] Unknown Error : " + e);
            return ResponseEntity.internalServerError().body(Collections.emptyList());
        }
    }
}

