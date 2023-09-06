package com.soccer.matchUp.dto.request;

import com.soccer.matchUp.entity.Scorer;

public record ScorerRequest(
        Long id,
        String name,
        Integer number,
        Integer goalCount
) {
    public static ScorerRequest of(
            Long id,
            String name,
            Integer number,
            Integer goalCount
    ){
        return new ScorerRequest(id, name, number, goalCount);
    }

    public static ScorerRequest from(Scorer scorer) {
        return ScorerRequest.of(
                scorer.getId(),
                scorer.getName(),
                scorer.getNumber(),
                scorer.getGoalCount()
        );
    }



    public Scorer toScorer() {
        return Scorer.builder()
                .name(name())
                .number(number())
                .goalCount(goalCount())
                .build();
    }
}
