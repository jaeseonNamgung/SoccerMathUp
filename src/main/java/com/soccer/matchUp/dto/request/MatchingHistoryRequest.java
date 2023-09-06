package com.soccer.matchUp.dto.request;

import com.soccer.matchUp.entity.MatchingHistory;

import java.time.LocalDateTime;

public record MatchingHistoryRequest(
        Long id,
        String homeTeamName,
        String awayTeamName,
        String score,
        String matchHistoryAgainst,
        String detail,
        LocalDateTime matchDate
) {

    public static MatchingHistoryRequest of(
            Long id,
            String homeTeamName,
            String awayTeamName,
            String score,
            String matchHistoryAgainst,
            String detail,
            LocalDateTime matchDate
    ){
        return new MatchingHistoryRequest(id,homeTeamName, awayTeamName, score, matchHistoryAgainst, detail,matchDate);
    }



    public MatchingHistory toMathingHistory() {
        return MatchingHistory.builder()
                .homeTeamName(homeTeamName())
                .awayTeamName(awayTeamName())
                .score(score())
                .matchHistoryAgainst(matchHistoryAgainst())
                .detail(detail())
                .matchDate(matchDate())
                .build();
    }



}
