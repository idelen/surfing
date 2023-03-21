package com.jackpot.surfing.api.domain;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface BlogSearchKeywordsRepository extends JpaRepository<BlogSearchKeywords, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BlogSearchKeywords> findByKeyword(String query);
}
