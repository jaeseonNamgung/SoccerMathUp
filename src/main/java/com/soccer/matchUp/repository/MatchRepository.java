package com.soccer.matchUp.repository;

import com.soccer.matchUp.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {

    /*
    * 1. 팀 매칭 기록 저장
    * 2. 단건 조회
    * */


}
