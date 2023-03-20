package com.jackpot.surfing.api.dto;

import com.jackpot.surfing.api.validator.PageAndSizeLimit;
import com.jackpot.surfing.api.validator.Sort;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class BlogSearchCondition {
    @NotBlank
    private String query;
    @Sort
    private String sort;
    @PageAndSizeLimit
    private Integer page;
    @PageAndSizeLimit
    private Integer size;

    public BlogSearchCondition() {
        this.sort = BlogSearchSortOption.ACCURACY.getSortOption();
        this.page = 1;
        this.size = 10;
    }

    public void setSort(String sort) {
        if (!sort.isBlank()) {
            this.sort = sort;
        }
    }

    public void setPage(Integer page) {
        if (!ObjectUtils.isEmpty(page)) {
            this.page = page;
        }
    }

    public void setSize(Integer size) {
        if (!ObjectUtils.isEmpty(size)) {
            this.size = size;
        }
    }
}
