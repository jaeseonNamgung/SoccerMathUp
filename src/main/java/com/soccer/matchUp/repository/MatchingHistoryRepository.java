package com.soccer.matchUp.repository;

import com.soccer.matchUp.entity.MatchingHistory;
import com.soccer.matchUp.repository.searchAndOrder.MatchingHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MatchingHistoryRepository extends JpaRepository<MatchingHistory, Long> , MatchingHistoryRepositoryCustom {

    /*
    * 1. 매칭 기록 저장
    * 2. 매칭 기록 단건 조회
    * 3. 매칭 기록 전체 조회 (페이징)
    * */

    @Query("select distinct m from MatchingHistory m join fetch m.scorers")
    Optional<MatchingHistory> findById(Long id);
}
