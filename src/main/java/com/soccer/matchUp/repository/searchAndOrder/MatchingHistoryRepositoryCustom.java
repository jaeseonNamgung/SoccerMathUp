package com.soccer.matchUp.repository.searchAndOrder;

import com.soccer.matchUp.entity.MatchingHistory;
import com.soccer.matchUp.repository.searchAndOrder.searchCondition.MatchingHistorySearchCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchingHistoryRepositoryCustom {
    List<MatchingHistory> findSearchAndSortAll(MatchingHistorySearchCondition mhSearchCond, Pageable pageable);
}
