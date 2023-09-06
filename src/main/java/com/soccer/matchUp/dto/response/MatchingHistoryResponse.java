package com.soccer.matchUp.dto.response;

import com.soccer.matchUp.dto.request.ScorerRequest;
import com.soccer.matchUp.entity.MatchingHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record MatchingHistoryResponse(
        Long id,
        String homeTeamName,
        String awayTeamName,
        String score,
        String matchHistoryAgainst,
        String detail,
        LocalDateTime matchDate,
        List<ScorerRequest> scorer
) {

    public static MatchingHistoryResponse fromResponse(MatchingHistory matchingHistory) {
        return MatchingHistoryResponse.of(
                matchingHistory.getId(),
                matchingHistory.getHomeTeamName(),
                matchingHistory.getAwayTeamName(),
                matchingHistory.getScore(),
                matchingHistory.getMatchHistoryAgainst(),
                matchingHistory.getDetail(),
                matchingHistory.getMatchDate(),
                matchingHistory.getScorers().stream().map(ScorerRequest::from).collect(Collectors.toList())
        );
    }

    public static MatchingHistoryResponse of(
            Long id,
            String homeTeamName,
            String awayTeamName,
            String score,
            String matchHistoryAgainst,
            String detail,
            LocalDateTime matchDate,
            List<ScorerRequest> scorer
    ){
        return new MatchingHistoryResponse(id, homeTeamName, awayTeamName, score, matchHistoryAgainst, detail,matchDate, scorer);
    }
}
