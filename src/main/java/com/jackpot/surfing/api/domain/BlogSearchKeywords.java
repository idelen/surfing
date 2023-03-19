package com.jackpot.surfing.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(indexes = @Index(name = "idx_keyword", columnList = "keyword", unique = true))
public class BlogSearchKeywords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    private int count;

    public BlogSearchKeywords(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }
}
