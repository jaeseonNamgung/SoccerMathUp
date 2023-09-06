package com.soccer.matchUp.dto.response;

import com.soccer.matchUp.entity.Scorer;

public record ScorerResponse(
        Long id,
        String name,
        Integer number,
        Integer goalCount
) {

    public static ScorerResponse of(
            Long id,
            String name,
            Integer number,
            Integer goalCount
    ){
        return new ScorerResponse(id, name, number, goalCount);
    }

        public static ScorerResponse from(Scorer scorer) {
        return ScorerResponse.of(
                scorer.getId(),
                scorer.getName(),
                scorer.getNumber(),
                scorer.getGoalCount()
        );
    }
}
