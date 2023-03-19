package com.jackpot.surfing.api.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogSearchKeywordsRepository extends JpaRepository<BlogSearchKeywords, Long> {
    Optional<BlogSearchKeywords> findByKeyword(String query);
}
