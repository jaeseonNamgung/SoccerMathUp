package com.soccer.matchUp.repository.searchAndOrder.searchCondition;

public record MatchingHistorySearchCondition(
        String homeTeamName,
        String awayTeamName,
        String scorer,
        String matchHistoryAgainst,
        String detail
) {
    public static MatchingHistorySearchCondition of(
            String homeTeamName,
            String awayTeamName,
            String scorer,
            String matchHistoryAgainst,
            String detail
    ){
        return new MatchingHistorySearchCondition(homeTeamName, awayTeamName, scorer, matchHistoryAgainst, detail);
    }
}
